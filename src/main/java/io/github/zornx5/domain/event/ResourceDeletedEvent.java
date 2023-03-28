package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 资源删除事件接口
 *
 * @author zornx5
 */
public interface ResourceDeletedEvent<U, PK extends Serializable> extends ResourceEvent<U, PK> {
}
