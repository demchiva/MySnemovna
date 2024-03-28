package cz.my.snemovna.rest.api;

import cz.my.snemovna.dto.auth.AuthenticationRequestDto;
import cz.my.snemovna.dto.auth.AuthenticationResponseDto;
import cz.my.snemovna.dto.auth.RefreshTokenRequestDto;
import cz.my.snemovna.dto.auth.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * The class for authorization and authentication of users.
 */
@Tag(name = "Auth", description = "Endpoints for authorization and authentication of users.")
@Path("/api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Produces(MediaType.APPLICATION_JSON_VALUE)
public interface IRestAuthentication {

    /**
     * The method for registering a user account with email and password.
     * @param request the register request.
     * @return registered and authenticated user access data.
     */
    @Operation(summary = "Register user account with email and password.")
    @POST
    @Path("/register")
    ResponseEntity<AuthenticationResponseDto> register(RegisterRequestDto request);

    /**
     * The method for authentication of user with email and password.
     * @param request the authentication request.
     * @return authentication user access data.
     */
    @Operation(summary = "Authenticate user with email and password.")
    @POST
    @Path("/authenticate")
    ResponseEntity<AuthenticationResponseDto> authenticate(AuthenticationRequestDto request);

    /**
     * The method for creating new access token by refresh token.
     * @param request the refresh token request.
     * @return authentication user access data.
     */
    @Operation(summary = "Create new access token by refresh token.")
    @POST
    @Path("/refreshToken")
    ResponseEntity<AuthenticationResponseDto> refreshToken(RefreshTokenRequestDto request);
}
