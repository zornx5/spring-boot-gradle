package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 资源更新事件接口
 *
 * @author zornx5
 */
public interface ResourceUpdatedEvent<U, PK extends Serializable> extends ResourceEvent<U, PK> {
}
