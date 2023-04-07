package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.DomainEvent;

import java.io.Serializable;

/**
 * 角色事件接口
 *
 * @author zornx5
 */
public interface RoleEvent<U extends User<U, PK>, PK extends Serializable> extends DomainEvent {
    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    Role<U, PK> getRole();
}
