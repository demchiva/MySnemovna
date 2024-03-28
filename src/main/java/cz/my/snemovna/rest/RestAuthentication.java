package cz.my.snemovna.rest;

import cz.my.snemovna.dto.auth.AuthenticationRequestDto;
import cz.my.snemovna.dto.auth.AuthenticationResponseDto;
import cz.my.snemovna.dto.auth.RefreshTokenRequestDto;
import cz.my.snemovna.dto.auth.RegisterRequestDto;
import cz.my.snemovna.rest.api.IRestAuthentication;
import cz.my.snemovna.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The api for authorization and authentication of users.
 */
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class RestAuthentication implements IRestAuthentication {

    private final AuthenticationService authenticationService;

    /**
     * The method for registering a user account with email and password.
     * @param request the register request.
     * @return registered and authenticated user access data.
     */
    @PostMapping("/register")
    @Override
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * The method for authentication of user with email and password.
     * @param request the authentication request.
     * @return authentication user access data.
     */
    @PostMapping("/authenticate")
    @Override
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * The method for creating new access token by refresh token.
     * @param request the refresh token request.
     * @return authentication user access data.
     */
    @PostMapping("/refreshToken")
    @Override
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
