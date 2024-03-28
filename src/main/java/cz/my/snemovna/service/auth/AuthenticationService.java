package cz.my.snemovna.service.auth;

import cz.my.snemovna.config.security.JwtHelper;
import cz.my.snemovna.dto.auth.AuthenticationRequestDto;
import cz.my.snemovna.dto.auth.AuthenticationResponseDto;
import cz.my.snemovna.dto.auth.RefreshTokenRequestDto;
import cz.my.snemovna.dto.auth.RegisterRequestDto;
import cz.my.snemovna.jpa.model.token.Token;
import cz.my.snemovna.jpa.model.token.TokenType;
import cz.my.snemovna.jpa.model.users.User;
import cz.my.snemovna.jpa.repository.token.TokenRepository;
import cz.my.snemovna.jpa.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * The service class for manage authentication.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    /**
     * The method authenticates user. If given in request credentials are invalid, the method throws {@link AuthenticationException}.
     * Otherwise, the method generates JWT token and refresh token.
     * @param request the authentication request.
     * @return authentication user access data.
     */
    public AuthenticationResponseDto authenticate(final AuthenticationRequestDto request) {
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
        return AuthenticationResponseDto.builder()
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

    /**
     * The method creates new access token by refresh token.
     * If refresh token is invalid, the method throws {@link ResponseStatusException}.
     * @param request the authentication request.
     * @return authentication user access data.
     */
    public AuthenticationResponseDto refreshToken(final RefreshTokenRequestDto request) {
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

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * The method creates new user account with email and password.
     * @param request the authentication request.
     * @return authentication user access data.
     */
    public AuthenticationResponseDto register(final RegisterRequestDto request) {
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
        
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
