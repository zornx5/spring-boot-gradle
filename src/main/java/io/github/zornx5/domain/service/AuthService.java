package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.interfaces.dto.UserChangePasswordRequest;
import io.github.zornx5.interfaces.dto.UserChangePasswordResponse;
import io.github.zornx5.interfaces.dto.UserInfoResponse;
import io.github.zornx5.interfaces.dto.UsernameLoginRequest;
import io.github.zornx5.interfaces.dto.UsernameLoginResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

/**
 * 权限服务接口
 *
 * @author zornx5
 */
public interface AuthService<U extends User<U, PK>, PK extends Serializable> {

    /**
     * 登陆
     *
     * @return 用户名登陆请求
     */
    UsernameLoginResponse login(UsernameLoginRequest request);

    /**
     * 登出
     */
    void logout();

    /**
     * 刷新 Token
     *
     * @param request 请求
     * @return 刷新后 Token
     */
    UsernameLoginResponse refreshToken(HttpServletRequest request);

    /**
     * 获取当前用户详细信息
     *
     * @return 用户详细信息
     */
    UserInfoResponse<U, PK> getUserInfo();

    /**
     * 修改当前用户密码
     *
     * @param request 修改密码请求
     * @return 修改后的信息
     */
    UserChangePasswordResponse changePassword(UserChangePasswordRequest request);
}
