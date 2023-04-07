package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.DomainEvent;

import java.io.Serializable;

/**
 * 用户事件接口
 *
 * @author zornx5
 */
public interface UserEvent<U extends User<U, PK>, PK extends Serializable> extends DomainEvent {
    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    User<U, PK> getUser();
}
