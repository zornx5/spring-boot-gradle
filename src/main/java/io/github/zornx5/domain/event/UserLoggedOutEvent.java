package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 用户登出事件接口
 *
 * @author zornx5
 */
public interface UserLoggedOutEvent<U, PK extends Serializable> extends UserEvent<U, PK> {
}
