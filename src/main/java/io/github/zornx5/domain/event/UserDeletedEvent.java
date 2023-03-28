package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 用户删除事件接口
 *
 * @author zornx5
 */
public interface UserDeletedEvent<U, PK extends Serializable> extends UserEvent<U, PK> {
}
