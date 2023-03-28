package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 用户创建事件接口
 *
 * @author zornx5
 */
public interface UserRegisteredEvent<U, PK extends Serializable> extends UserEvent<U, PK> {
}
