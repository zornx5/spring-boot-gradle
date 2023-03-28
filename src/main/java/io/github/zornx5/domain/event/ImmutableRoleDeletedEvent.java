package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Role;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * 不可变角色删除事件
 *
 * @author zornx5
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ImmutableRoleDeletedEvent<U, PK extends Serializable> extends AbstractRoleEvent<U, PK>
        implements RoleDeletedEvent<U, PK> {
    public ImmutableRoleDeletedEvent(Role<U, PK> role) {
        super(role);
    }
}
