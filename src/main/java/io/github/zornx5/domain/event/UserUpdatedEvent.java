package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 用户更新事件接口
 *
 * @author zornx5
 */
public interface UserUpdatedEvent<U, PK extends Serializable> extends UserEvent<U, PK> {
}
