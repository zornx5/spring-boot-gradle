package io.github.zornx5.domain.entity;

import io.github.zornx5.infrastructure.common.DomainBuilder;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 资源接口
 *
 * @author zornx5
 */
public interface Resource<U, PK extends Serializable> extends Nameable<PK>,
        Auditable<U, PK, LocalDateTime>, DomainBuilder.ToBuilder<Resource.Builder<U, PK>> {

    void create();

    ResourceType getType();

    void setType(ResourceType type);

    String getPermission();

    void setPermission(String permission);

    String getIcon();

    void setIcon(String icon);

    String getUrl();

    void setUrl(String url);

    Collection<Resource<U, PK>> getChildren();

    void setChildren(Collection<Resource<U, PK>> children);

    interface Builder<U, PK extends Serializable> extends DomainBuilder<Resource<U, PK>> {
        Builder<U, PK> id(PK id);

        Builder<U, PK> name(String name);

        Builder<U, PK> description(String description);

        Builder<U, PK> type(ResourceType type);

        Builder<U, PK> permission(String permission);

        Builder<U, PK> icon(String icon);

        Builder<U, PK> url(String url);

        Builder<U, PK> children(Collection<Resource<U, PK>> children);
    }
}