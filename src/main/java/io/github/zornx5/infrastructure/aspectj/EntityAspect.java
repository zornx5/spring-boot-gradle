package io.github.zornx5.infrastructure.aspectj;

import io.github.zornx5.domain.entity.AbstractAuditable;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.service.UserService;
import io.github.zornx5.infrastructure.repository.UserQuery;
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

/**
 * 实体切面
 *
 * @author zornx5
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EntityAspect<U extends User<U, PK>, PK extends Serializable> {

    private final UserService<U, PK> userService;

    @Before("execution(* io.github.zornx5.domain.service.*.update(..)) && args(entity)")
    public void beforeUpdateEntity(AbstractAuditable<U, PK> entity) {

        log.info("更新方法切面处理");
        User<U, PK> user = getUser();
        entity.setLastModifiedBy((U) user);
        entity.setLastModifiedDate(LocalDateTime.now());
    }

    private User<U, PK> getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User<U, PK> user = null;
        if (!Objects.isNull(authentication) && Strings.isNotBlank(authentication.getName())) {
            user = userService.findByQuery(UserQuery.nameOf(authentication.getName())).orElse(user);
        }
        return user;
    }

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

}