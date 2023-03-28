package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * 不可变用户更新事件
 *
 * @author zornx5
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ImmutableUserUpdatedEvent<U, PK extends Serializable> extends AbstractUserEvent<U, PK>
        implements UserUpdatedEvent<U, PK> {
    public ImmutableUserUpdatedEvent(User<U, PK> user) {
        super(user);
    }
}
