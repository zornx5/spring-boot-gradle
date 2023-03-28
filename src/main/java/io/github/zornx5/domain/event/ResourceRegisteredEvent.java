package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 资源创建事件接口
 *
 * @author zornx5
 */
public interface ResourceRegisteredEvent<U, PK extends Serializable> extends ResourceEvent<U, PK> {
}
