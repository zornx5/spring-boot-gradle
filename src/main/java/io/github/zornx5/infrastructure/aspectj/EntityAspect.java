package io.github.zornx5.infrastructure.aspectj;

import io.github.zornx5.domain.entity.AbstractAuditable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体切面
 *
 * @author zornx5
 */
@Aspect
@Component
@Slf4j
public class EntityAspect<U, PK extends Serializable> {

    @Before("execution(* io.github.zornx5.domain.service.*.update(..)) && args(entity)")
    public void beforeUpdateEntity(AbstractAuditable<U, PK> entity) {
        log.info("更新方法切面处理");
        entity.setLastModifiedDate(LocalDateTime.now());
    }

    @Before("execution(* io.github.zornx5.domain.service.*.save(..)) && args(entity)")
    public void beforeSaveEntity(AbstractAuditable<U, PK> entity) {
        log.info("保存方法切面处理");
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastModifiedDate(now);
    }

}