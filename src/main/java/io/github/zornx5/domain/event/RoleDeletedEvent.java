package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 角色删除事件接口
 *
 * @author zornx5
 */
public interface RoleDeletedEvent<U, PK extends Serializable> extends RoleEvent<U, PK> {
}
