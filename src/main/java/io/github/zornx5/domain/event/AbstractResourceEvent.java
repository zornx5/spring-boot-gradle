package io.github.zornx5.domain.event;

import io.github.zornx5.domain.entity.Resource;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 资源事件支持
 *
 * @author zornx5
 */
@Getter
public abstract class AbstractResourceEvent<U, PK extends Serializable> extends ApplicationEvent
        implements ResourceEvent<U, PK> {

    /**
     * 资源
     */
    private final Resource<U, PK> resource;

    public AbstractResourceEvent(Resource<U, PK> resource) {
        super(resource);
        this.resource = resource;
    }
}
