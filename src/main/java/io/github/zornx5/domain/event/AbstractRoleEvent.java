package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Role;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 角色事件支持
 *
 * @author zornx5
 */
@Getter
public abstract class AbstractRoleEvent<U, PK extends Serializable> extends ApplicationEvent
        implements RoleEvent<U, PK> {

    /**
     * 角色
     */
    private final Role<U, PK> role;

    public AbstractRoleEvent(Role<U, PK> role) {
        super(role);
        this.role = role;
    }
}
