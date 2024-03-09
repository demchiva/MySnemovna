package cz.my.snemovna.service.auth;

import cz.my.snemovna.config.security.JwtHelper;
import cz.my.snemovna.dto.auth.AuthenticationRequest;
import cz.my.snemovna.dto.auth.AuthenticationResponse;
import cz.my.snemovna.dto.auth.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

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
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(final RefreshTokenRequest request) {
        final String refreshToken = request.getRefreshToken();
        final String username = jwtHelper.extractUsername(refreshToken);
        final boolean isTokenValid = jwtHelper.validateToken(refreshToken, username);
        if (isTokenValid && username != null) {
            final String accessToken = jwtHelper.generateJwtToken(username);
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is expired.");
    }
}
