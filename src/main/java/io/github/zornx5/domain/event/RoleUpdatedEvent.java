package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 角色更新事件接口
 *
 * @author zornx5
 */
public interface RoleUpdatedEvent<U, PK extends Serializable> extends RoleEvent<U, PK> {
}
