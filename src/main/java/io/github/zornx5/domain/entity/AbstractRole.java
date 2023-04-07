package io.github.zornx5.domain.entity;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Collection;

/**
 * 抽象角色
 *
 * @author zornx5
 */
@MappedSuperclass
public abstract class AbstractRole<U extends User<U, PK>, PK extends Serializable> extends AbstractNameable<U, PK>
        implements Role<U, PK> {

    private static final long serialVersionUID = 14130110092L;

    @Override
    public void init() {
    }

    @Override
    public Builder<U, PK> toBuilder() {
        return new AbstractBuilder<>(this) {
        };
    }

    protected abstract static class AbstractBuilder<U extends User<U, PK>, PK extends Serializable> implements Builder<U, PK> {

        private final AbstractRole<U, PK> role;

        public AbstractBuilder(AbstractRole<U, PK> role) {
            this.role = role;
        }

        @Override
        public Role<U, PK> build() {
            return this.role;
        }

        @Override
        public Builder<U, PK> id(PK id) {
            this.role.setId(id);
            return this;
        }

        @Override
        public Builder<U, PK> name(String name) {
            this.role.setName(name);
            return this;
        }

        @Override
        public Builder<U, PK> description(String description) {
            this.role.setDescription(description);
            return this;
        }

        @Override
        public Builder<U, PK> resources(Collection<Resource<U, PK>> resources) {
            this.role.setResources(resources);
            return this;
        }
    }
}
