package io.github.zornx5.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户修改密码请求
 *
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 * @author zornx5
 */
public record UserChangePasswordRequest(
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {
}
