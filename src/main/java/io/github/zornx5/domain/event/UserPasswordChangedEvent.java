package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 用户登录事件接口
 *
 * @author zornx5
 */
public interface UserPasswordChangedEvent<U, PK extends Serializable> extends UserUpdatedEvent<U, PK> {
}
