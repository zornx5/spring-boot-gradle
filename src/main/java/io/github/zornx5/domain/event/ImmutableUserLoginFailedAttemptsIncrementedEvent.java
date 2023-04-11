package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * 不可变用户登陆事件
 *
 * @author zornx5
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ImmutableUserLoginFailedAttemptsIncrementedEvent<U extends User<U, PK>, PK extends Serializable>
        extends AbstractUserEvent<U, PK>
        implements UserLoginFailedAttemptsIncrementedEvent<U, PK> {
    public ImmutableUserLoginFailedAttemptsIncrementedEvent(User<U, PK> user) {
        super(user);
    }
}
