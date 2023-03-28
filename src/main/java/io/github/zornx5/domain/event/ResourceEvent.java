package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.DomainEvent;

import java.io.Serializable;

/**
 * 资源事件接口
 *
 * @author zornx5
 */
public interface ResourceEvent<U, PK extends Serializable> extends DomainEvent {
    /**
     * 获取资源信息
     *
     * @return 资源信息
     */
    Resource<U, PK> getResource();
}
