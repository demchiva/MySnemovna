package cz.my.snemovna.service.auth;

import cz.my.snemovna.config.security.JwtHelper;
import cz.my.snemovna.dto.auth.AuthenticationRequest;
import cz.my.snemovna.dto.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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
        final String jwtToken = jwtHelper.generateToken(userDetails.getUsername());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
