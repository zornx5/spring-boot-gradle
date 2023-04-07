package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.service.AuthService;
import io.github.zornx5.interfaces.dto.UserChangePasswordRequest;
import io.github.zornx5.interfaces.dto.UserChangePasswordResponse;
import io.github.zornx5.interfaces.dto.UserInfoResponse;
import io.github.zornx5.interfaces.dto.UsernameLoginRequest;
import io.github.zornx5.interfaces.dto.UsernameLoginResponse;
import io.github.zornx5.interfaces.facade.AuthApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 权限 Restful 资源
 *
 * @author zornx5
 */
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@Slf4j
@Tag(name = "权限 Restful 资源")
public class AuthRestResource<U extends User<U, PK>, PK extends Serializable> implements AuthApi<U, PK> {

    private final AuthService<U, PK> authService;

    @Override
    @PostMapping("/login")
    public UsernameLoginResponse login(UsernameLoginRequest request) {
        return authService.login(request);
    }

    @Override
    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }

    @Override
    @PatchMapping("/token")
    public UsernameLoginResponse refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @Override
    @GetMapping("/details")
    public UserInfoResponse<U, PK> getUserInfo() {
        return authService.getUserInfo();
    }

    @Override
    @PatchMapping("/password")
    public UserChangePasswordResponse changePassword(@RequestBody @Valid UserChangePasswordRequest request) {
        return authService.changePassword(request);
    }
}
