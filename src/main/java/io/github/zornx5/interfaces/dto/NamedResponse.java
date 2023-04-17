package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Nameable;

import java.io.Serializable;

public record NamedResponse<PK extends Serializable>(
        PK id,
        String name) {
    public static <PK extends Serializable> NamedResponse<PK> of(Nameable<PK> nameable) {
        return new NamedResponse<>(
                nameable.getId(),
                nameable.getName());
    }
}
