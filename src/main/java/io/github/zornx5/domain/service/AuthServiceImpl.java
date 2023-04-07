package io.github.zornx5.domain.service;

import com.google.common.net.HttpHeaders;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.JwtService;
import io.github.zornx5.infrastructure.JwtUserDetails;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.interfaces.dto.UserChangePasswordRequest;
import io.github.zornx5.interfaces.dto.UserChangePasswordResponse;
import io.github.zornx5.interfaces.dto.UserInfoResponse;
import io.github.zornx5.interfaces.dto.UsernameLoginRequest;
import io.github.zornx5.interfaces.dto.UsernameLoginResponse;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static io.github.zornx5.infrastructure.filter.JwtAuthenticationFilter.BEARER;

/**
 * 角色服务实现
 *
 * @author zornx5
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
@Slf4j
public class AuthServiceImpl<U extends User<U, PK>, PK extends Serializable>
        implements ApplicationEventPublisherAware, AuthService<U, PK> {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService<U, PK> userService;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(@Nonnull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public UsernameLoginResponse login(UsernameLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = userService.findByQuery(UserQuery.nameOf(request.username()))
                .orElseThrow(UserNotFoundException::new);
        var userDetails = new JwtUserDetails<>(user);
        var token = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        return new UsernameLoginResponse(user.getName(), token, refreshToken);
    }

    @Override
    public void logout() {

    }

    @Override
    public UsernameLoginResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.trim().startsWith(BEARER)) {
            return null;
        }
        final String refreshToken = authHeader.replace(BEARER, "").trim();
        final String subject = jwtService.extractSubject(refreshToken);
        if (subject != null) {
            var user = userService.findByQuery(UserQuery.nameOf(subject))
                    .orElseThrow(UserNotFoundException::new);
            var userDetails = new JwtUserDetails<>(user);
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var token = jwtService.generateToken(userDetails);
                return new UsernameLoginResponse(user.getName(), token, refreshToken);
            }
        }
        return null;
    }

    @Override
    public UserInfoResponse<U, PK> getUserInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.findByQuery(UserQuery.nameOf(authentication.getName()))
                .orElseThrow(UserNotFoundException::new);
        return UserInfoResponse.of(user);
    }

    @Override
    public UserChangePasswordResponse changePassword(UserChangePasswordRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authentication.getName(),
                        request.oldPassword()
                )
        );
        var user = userService.findByQuery(UserQuery.nameOf(authentication.getName()))
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        var saveUser = userService.save(user);
        return new UserChangePasswordResponse(saveUser.getName(), request.newPassword());
    }
}
