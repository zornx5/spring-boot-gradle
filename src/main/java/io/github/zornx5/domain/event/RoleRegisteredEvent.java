package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;

import java.io.Serializable;

/**
 * 角色创建事件接口
 *
 * @author zornx5
 */
public interface RoleRegisteredEvent<U extends User<U, PK>, PK extends Serializable> extends RoleEvent<U, PK> {
}
