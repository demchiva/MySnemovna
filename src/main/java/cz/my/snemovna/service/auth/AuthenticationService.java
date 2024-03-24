package cz.my.snemovna.service.auth;

import cz.my.snemovna.config.security.JwtHelper;
import cz.my.snemovna.dto.auth.AuthenticationRequest;
import cz.my.snemovna.dto.auth.AuthenticationResponse;
import cz.my.snemovna.dto.auth.RefreshTokenRequest;
import cz.my.snemovna.dto.auth.RegisterRequest;
import cz.my.snemovna.jpa.model.token.Token;
import cz.my.snemovna.jpa.model.token.TokenType;
import cz.my.snemovna.jpa.model.users.User;
import cz.my.snemovna.jpa.repository.token.TokenRepository;
import cz.my.snemovna.jpa.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtHelper.generateJwtToken(userDetails.getUsername());
        final String refreshToken = jwtHelper.generateRefreshToken(userDetails.getUsername());

        final User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        revokeAllUserTokens(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(final User savedUser, final String jwtToken) {
        final Token token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(true)
                .build();

        tokenRepository.save(token);
    }

    public AuthenticationResponse refreshToken(final RefreshTokenRequest request) {
        final String refreshToken = request.getRefreshToken();

        final String usernameFromToken = jwtHelper.extractUsername(refreshToken);
        final boolean isTokenValid = jwtHelper.validateToken(refreshToken, usernameFromToken);

        if (usernameFromToken == null || !isTokenValid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is expired.");
        }

        final User user = userRepository.findByEmail(usernameFromToken).orElseThrow();

        final String accessToken = jwtHelper.generateJwtToken(usernameFromToken);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse register(final RegisterRequest request) {
        final User user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        final String jwtToken = jwtHelper.generateJwtToken(request.getUsername());
        final String refreshToken = jwtHelper.generateRefreshToken(request.getUsername());

        final User savedUser = userRepository.save(user);
        saveUserToken(savedUser, jwtToken);
        
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
