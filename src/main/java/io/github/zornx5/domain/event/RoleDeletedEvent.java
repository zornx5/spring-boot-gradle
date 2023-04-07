package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;

import java.io.Serializable;

/**
 * 角色删除事件接口
 *
 * @author zornx5
 */
public interface RoleDeletedEvent<U extends User<U, PK>, PK extends Serializable> extends RoleEvent<U, PK> {
}
