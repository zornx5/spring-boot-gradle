package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;

import java.io.Serializable;

/**
 * 用户登录事件接口
 *
 * @author zornx5
 */
public interface UserPasswordChangedEvent<U extends User<U, PK>, PK extends Serializable> extends UserUpdatedEvent<U, PK> {
}
