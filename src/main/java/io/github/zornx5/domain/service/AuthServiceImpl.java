package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.ImmutableUserLoggedInEvent;
import io.github.zornx5.domain.event.ImmutableUserLoggedOutEvent;
import io.github.zornx5.domain.event.ImmutableUserLoginFailedAttemptsIncrementedEvent;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    @Value("${jwt.issuer:ZornX5}")
    private String issuer;
    @Value("${jwt.expiration:5}")
    private Duration expiration;
    @Value("${jwt.refresh-expiration:30}")
    private Duration refreshExpiration;

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
            Instant now = Instant.now();
            if (expiration.compareTo(refreshExpiration) > 0) {
                // expiration can't greater refreshExpiration
                expiration = refreshExpiration;
            }
            // @formatter:off
            String scope = upkUser.get().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer(issuer)
                    .issuedAt(now)
                    .expiresAt(now.plus(expiration))
                    .subject(upkUser.get().getName())
                    .claim("scope", scope)
                    .build();
            JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
                    .issuer(issuer)
                    .issuedAt(now)
                    .expiresAt(now.plus(refreshExpiration))
                    .subject(upkUser.get().getName())
                    .build();
            // @formatter:on
            String accessToken = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            // refresh accessToken just can refresh access accessToken
            String refreshToken = encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();
            eventPublisher.publishEvent(new ImmutableUserLoggedInEvent<>(upkUser.get()));
            return new UsernameLoginResponse(upkUser.get().getName(), accessToken, refreshToken);
        } else {
            throw new UserNotFoundException(request.username());
        }
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional.ofNullable(authentication.getPrincipal()).ifPresentOrElse(
                user -> {
                    if (user instanceof User<?, ?> loginUser) {
                        SecurityContextHolder.clearContext();
                        eventPublisher.publishEvent(new ImmutableUserLoggedOutEvent<>(loginUser));
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
        Jwt jwt = decoder.decode(refreshToken);
        String subject = jwt.getSubject();
        if (subject != null) {
            var user = userService.findByQuery(UserQuery.nameOf(subject))
                    .orElseThrow(UserNotFoundException::new);
            if (user.isEnabled() && user.isAccountNonLocked() && user.isAccountNonExpired()
                    && !Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now())) {
                Instant now = Instant.now();
                // @formatter:off
                String scope = user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" "));
                JwtClaimsSet claims = JwtClaimsSet.builder()
                        .issuer(issuer)
                        .issuedAt(now)
                        .expiresAt(now.plus(expiration))
                        .subject(user.getName())
                        .claim("scope", scope)
                        .build();
                // @formatter:on
                String accessToken = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
                return new UsernameLoginResponse(user.getName(), accessToken, refreshToken);
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
