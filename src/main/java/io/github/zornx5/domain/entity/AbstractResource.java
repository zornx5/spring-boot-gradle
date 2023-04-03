package io.github.zornx5.domain.entity;


import io.github.zornx5.infrastructure.common.enums.ResourceType;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * 抽象资源
 *
 * @author zornx5
 */
@MappedSuperclass
public abstract class AbstractResource<U, PK extends Serializable> extends AbstractNameable<U, PK>
        implements Resource<U, PK> {

    private static final long serialVersionUID = 14130110092L;

    @Override
    public void init() {
    }

    @Override
    public Builder<U, PK> toBuilder() {
        return new AbstractBuilder<>(this) {
        };
    }

    protected abstract static class AbstractBuilder<U, PK extends Serializable> implements Builder<U, PK> {

        private final AbstractResource<U, PK> resource;

        public AbstractBuilder(AbstractResource<U, PK> resource) {
            this.resource = resource;
        }

        @Override
        public Resource<U, PK> build() {
            return this.resource;
        }

        @Override
        public Builder<U, PK> id(PK id) {
            this.resource.setId(id);
            return this;
        }

        @Override
        public Builder<U, PK> name(String name) {
            this.resource.setName(name);
            return this;
        }

        @Override
        public Builder<U, PK> description(String description) {
            this.resource.setDescription(description);
            return this;
        }

        @Override
        public Builder<U, PK> type(ResourceType type) {
            this.resource.setType(type);
            return this;
        }

        @Override
        public Builder<U, PK> permission(String permission) {
            this.resource.setPermission(permission);
            return this;
        }

        @Override
        public Builder<U, PK> icon(String icon) {
            this.resource.setIcon(icon);
            return this;
        }

        @Override
        public Builder<U, PK> url(String url) {
            this.resource.setUrl(url);
            return this;
        }

        @Override
        public Builder<U, PK> parent(Resource<U, PK> parent) {
            this.resource.setParent(parent);
            return this;
        }
    }
}
