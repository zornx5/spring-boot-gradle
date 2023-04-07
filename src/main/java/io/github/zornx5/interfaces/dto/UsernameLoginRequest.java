package io.github.zornx5.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户登陆请求
 *
 * @param username 名称
 * @param password 密码
 * @author zornx5
 */
public record UsernameLoginRequest(
        @NotBlank(message = "用户名不能为空") String username,
        @NotBlank(message = "密码不能为空") String password
) {
}
