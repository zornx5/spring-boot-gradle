package io.github.zornx5.infrastructure.aspectj;

import io.github.zornx5.domain.entity.Nameable;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.Content;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用户实体切面
 *
 * @param <PK> 用户主键类型
 * @author zornx5
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserEntityAspect<U extends User<U, PK>, PK extends Serializable> {

    private final UserRepository<U, PK> userRepository;

    /**
     * 更新用户方法切面处理
     *
     * <p>禁止更新默认用户</p>
     *
     * @param entity 实体
     */
    @Before("execution(* io.github.zornx5.infrastructure.repository.UserRepository.save(..)) && args(entity)")
    public void beforeUpdateEntity(Nameable<PK> entity) {
        log.info("用户保存/更新方法切面处理");
        if (Content.SYSTEM_USER.equals(entity.getName()) &&
                userRepository.findByQuery(UserQuery.nameOf(Content.SYSTEM_USER)).isPresent()) {
            throw new UnsupportedOperationException("禁止保存/更新默认用户");
        }
    }

    /**
     * 删除用户方法切面处理
     *
     * <p>禁止删除默认用户</p>
     *
     * @param entity 实体
     */
    @Before("execution(* io.github.zornx5.infrastructure.repository.UserRepository.delete(..)) && args(entity)")
    public void beforeDeleteEntity(Nameable<PK> entity) {
        log.info("用户删除方法切面处理");
        if (Content.SYSTEM_USER.equals(entity.getName()) &&
                userRepository.findByQuery(UserQuery.nameOf(Content.SYSTEM_USER)).isPresent()) {
            throw new UnsupportedOperationException("禁止删除默认用户");
        }
    }
}
