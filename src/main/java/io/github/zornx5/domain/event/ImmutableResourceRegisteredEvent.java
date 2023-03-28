package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Resource;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * 不可变资源创建事件
 *
 * @author zornx5
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ImmutableResourceRegisteredEvent<U, PK extends Serializable> extends AbstractResourceEvent<U, PK>
        implements ResourceRegisteredEvent<U, PK> {
    public ImmutableResourceRegisteredEvent(Resource<U, PK> resource) {
        super(resource);
    }
}
