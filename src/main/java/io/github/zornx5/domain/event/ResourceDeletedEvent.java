package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.User;

import java.io.Serializable;

/**
 * 资源删除事件接口
 *
 * @author zornx5
 */
public interface ResourceDeletedEvent<U extends User<U, PK>, PK extends Serializable> extends ResourceEvent<U, PK> {
}
