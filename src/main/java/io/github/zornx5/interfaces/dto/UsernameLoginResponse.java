package io.github.zornx5.interfaces.dto;

public record UsernameLoginResponse(
        String username,
        String accessToken,
        String refreshToken
) {
}
