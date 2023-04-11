package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.ImmutableUserLoggedInEvent;
import io.github.zornx5.domain.event.ImmutableUserLoggedOutEvent;
import io.github.zornx5.domain.event.ImmutableUserLoginFailedAttemptsIncrementedEvent;
import io.github.zornx5.infrastructure.JwtService;
import io.github.zornx5.infrastructure.JwtUserDetails;
import io.github.zornx5.infrastructure.common.enums.ResponseStatus;
import io.github.zornx5.infrastructure.common.exception.BaseException;
import io.github.zornx5.infrastructure.common.exception.UserException;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

import static io.github.zornx5.infrastructure.util.TokenUtil.getAuthorizationToken;

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

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService<U, PK> userService;

    private final PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(@Nonnull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public UsernameLoginResponse login(UsernameLoginRequest request) {
        Optional<User<U, PK>> upkUser = userService.findByQuery(UserQuery.nameOf(request.username()));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            ));
        } catch (BadCredentialsException e) {
            upkUser.ifPresent(user -> {
                        log.debug("推送用户登陆失败次数增加事件");
                        eventPublisher.publishEvent(new ImmutableUserLoginFailedAttemptsIncrementedEvent<>(user));
                    }
            );
            throw new BaseException(ResponseStatus.BAD_CREDENTIALS, e);
        } catch (DisabledException e) {
            throw new UserException(ResponseStatus.USER_DISABLED, e);
        } catch (LockedException e) {
            throw new UserException(ResponseStatus.USER_LOCKED, e);
        } catch (Exception e) {
            throw new BaseException(ResponseStatus.ERROR, e);
        }
        if (upkUser.isPresent()) {
            var userDetails = new JwtUserDetails<>(upkUser.get());
            var token = jwtService.generateToken(userDetails);
            var refreshToken = jwtService.generateRefreshToken(userDetails);
            eventPublisher.publishEvent(new ImmutableUserLoggedInEvent<>(upkUser.get()));
            return new UsernameLoginResponse(upkUser.get().getName(), token, refreshToken);
        } else {
            throw new UserNotFoundException(request.username());
        }
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional.ofNullable(authentication.getPrincipal()).ifPresentOrElse(
                userDetails -> {
                    if (userDetails instanceof JwtUserDetails) {
                        JwtUserDetails<U, PK> jwtUserDetails = (JwtUserDetails<U, PK>) userDetails;
                        SecurityContextHolder.clearContext();
                        eventPublisher.publishEvent(new ImmutableUserLoggedOutEvent<>(jwtUserDetails.getUser()));
                    } else {
                        throw new UnsupportedOperationException();
                    }
                },
                () -> {
                    throw new BaseException(ResponseStatus.BAD_CREDENTIALS);
                }
        );
    }

    @Override
    public UsernameLoginResponse refreshToken(HttpServletRequest request) {
        String refreshToken = getAuthorizationToken(request)
                .orElseThrow(() -> new BaseException(ResponseStatus.BAD_CREDENTIALS));
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
