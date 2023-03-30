package io.github.zornx5.domain.entity;

import io.github.zornx5.infrastructure.common.DomainBuilder;
import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 角色接口
 *
 * @author zornx5
 */
public interface Role<U, PK extends Serializable> extends Nameable<PK>,
        Auditable<U, PK, LocalDateTime>, DomainBuilder.ToBuilder<Role.Builder<U, PK>> {

    /**
     * 创建对象初始化内容
     */
    void init();

    Collection<Resource<U, PK>> getResources();

    void setResources(Collection<Resource<U, PK>> resources);

    interface Builder<U, PK extends Serializable> extends DomainBuilder<Role<U, PK>> {
        Builder<U, PK> id(PK id);

        Builder<U, PK> name(String name);

        Builder<U, PK> description(String description);

        Builder<U, PK> resources(Collection<Resource<U, PK>> resources);
    }
}
