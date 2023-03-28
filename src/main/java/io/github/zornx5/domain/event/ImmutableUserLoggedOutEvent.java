package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * 不可变用户登出事件
 *
 * @author zornx5
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ImmutableUserLoggedOutEvent<U, PK extends Serializable> extends AbstractUserEvent<U, PK>
        implements UserLoggedOutEvent<U, PK> {
    public ImmutableUserLoggedOutEvent(User<U, PK> user) {
        super(user);
    }
}
