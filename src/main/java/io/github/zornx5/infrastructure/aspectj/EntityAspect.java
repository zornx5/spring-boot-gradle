package io.github.zornx5.infrastructure.aspectj;

import io.github.zornx5.domain.entity.AbstractAuditable;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.Content;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.infrastructure.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 实体切面
 *
 * <p>该类提供了在实体更新和保存时自动设置实体的创建者、修改者、创建时间和修改时间的功能。</p>
 *
 * <p>使用方法：在需要使用该功能的实体服务方法上添加相应的切面注解即可。</p>
 *
 * <p>切面注解：</p>
 * <ul>
 *     <li>更新方法：{@code @Before("execution(* io.github.zornx5.domain.service.*.update(..)) && args(entity)")}</li>
 *     <li>保存方法：{@code @Before("execution(* io.github.zornx5.domain.service.*.save(..)) && args(entity)")}</li>
 * </ul>
 *
 * @param <U>  用户类型
 * @param <PK> 用户主键类型
 * @author zornx5
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EntityAspect<U extends User<U, PK>, PK extends Serializable> {

    private final UserRepository<U, PK> userRepository;

    @PostConstruct
    public void init() {
        log.info("EntityAspect init");
        var systemuser = userRepository.save(userRepository.create().toBuilder()
                .name(Content.SYSTEM_USER)
                .description("系统默认用户")
                .firstName("username")
                .lastName("system")
                .email("system-user@zornx5.github.io")
                .phone("00000000000")
                .password(UUID.randomUUID().toString())
                .status(UserStatus.NO_LOGIN)
                .gender(UserGender.UNKNOWN)
                .expiredTime(LocalDateTime.now())
                .roles(null)
                .build());
        log.info("created system default user: {}", systemuser);
    }

    /**
     * 更新方法切面处理
     *
     * <p>该方法会在实体更新前自动设置实体的修改者和修改时间。</p>
     *
     * @param entity 实体
     */
    @Before("execution(* io.github.zornx5.domain.service.*.update(..)) && args(entity)")
    public void beforeUpdateEntity(AbstractAuditable<U, PK> entity) {
        log.info("更新方法切面处理");
        User<U, PK> user = getUser();
        entity.setLastModifiedBy((U) user);
        entity.setLastModifiedDate(LocalDateTime.now());
    }

    /**
     * 保存方法切面处理
     *
     * <p>该方法会在实体保存前自动设置实体的创建者、修改者、创建时间和修改时间。</p>
     *
     * @param entity 实体
     */
    @Before("execution(* io.github.zornx5.domain.service.*.save(..)) && args(entity)")
    public void beforeSaveEntity(AbstractAuditable<U, PK> entity) {
        log.info("保存方法切面处理");
        User<U, PK> user = getUser();
        entity.setCreatedBy((U) user);
        entity.setLastModifiedBy((U) user);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastModifiedDate(now);
    }

    /**
     * 获取当前用户
     *
     * <p>该方法会从 Spring Security 的上下文中获取当前用户。</p>
     *
     * @return 当前用户，如果不存在则返回 null
     */
    @Nullable
    private User<U, PK> getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || Strings.isBlank(authentication.getName())) {
            return userRepository.findByQuery(UserQuery.nameOf(Content.SYSTEM_USER))
                    .orElseThrow(() -> new UserNotFoundException("system default user not found"));
        }
        return userRepository.findByQuery(UserQuery.nameOf(authentication.getName()))
                .orElseThrow(() -> new UserNotFoundException(authentication.getName()));
    }

}
