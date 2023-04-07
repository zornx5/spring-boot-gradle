package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * 不可变角色创建事件
 *
 * @author zornx5
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ImmutableRoleRegisteredEvent<U extends User<U, PK>, PK extends Serializable> extends AbstractRoleEvent<U, PK>
        implements RoleRegisteredEvent<U, PK> {
    public ImmutableRoleRegisteredEvent(Role<U, PK> role) {
        super(role);
    }
}
