package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 角色创建事件接口
 *
 * @author zornx5
 */
public interface RoleRegisteredEvent<U, PK extends Serializable> extends RoleEvent<U, PK> {
}
