package io.github.zornx5.domain.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.MappedSuperclass;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 可命名的抽象基类
 *
 * @author zornx5
 */
@MappedSuperclass
public abstract class AbstractNameable<U, PK extends Serializable> extends AbstractAuditable<U, PK>
        implements Nameable<PK> {

    private static final long serialVersionUID = 14130110092L;

    private @Nonnull String name;

    private @Nullable String description;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@Nullable String description) {
        this.description = description;
    }


}