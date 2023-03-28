package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

public record RoleResponse<U, PK extends Serializable>(
        PK id,
        String name,
        String description,
        Optional<U> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<U> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate
) {
    public static <U, PK extends Serializable> RoleResponse<U, PK> of(Role<U, PK> role) {
        return new RoleResponse<>(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getCreatedBy(),
                role.getCreatedDate(),
                role.getLastModifiedBy(),
                role.getLastModifiedDate()
        );
    }
}
