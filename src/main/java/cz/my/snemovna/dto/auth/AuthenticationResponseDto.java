package cz.my.snemovna.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Authentication response. Contains access token and refresh token.

 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto implements Serializable {
    private String accessToken;
    private String refreshToken;
}
