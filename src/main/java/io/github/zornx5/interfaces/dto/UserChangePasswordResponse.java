package io.github.zornx5.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户修改密码响应
 *
 * @param username 用户名
 * @param password 密码
 * @author zornx5
 */
public record UserChangePasswordResponse(
        @NotBlank String username,
        @NotBlank String password
) {
}
