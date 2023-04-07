package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 用户事件支持
 *
 * @author zornx5
 */
@Getter
public abstract class AbstractUserEvent<U extends User<U, PK>, PK extends Serializable> extends ApplicationEvent
        implements UserEvent<U, PK> {

    /**
     * 用户
     */
    private final User<U, PK> user;

    public AbstractUserEvent(User<U, PK> user) {
        super(user);
        this.user = user;
    }
}
