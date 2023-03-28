package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.enums.ResourceType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

public record ResourceResponse<U, PK extends Serializable>(
        PK id,
        String name,
        String description,
        ResourceType type,
        String permission,
        String icon,
        String url,
        Optional<U> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<U> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate
) {
    public static <U, PK extends Serializable> ResourceResponse<U, PK> of(Resource<U, PK> resource) {
        return new ResourceResponse<>(
                resource.getId(),
                resource.getName(),
                resource.getDescription(),
                resource.getType(),
                resource.getPermission(),
                resource.getIcon(),
                resource.getUrl(),
                resource.getCreatedBy(),
                resource.getCreatedDate(),
                resource.getLastModifiedBy(),
                resource.getLastModifiedDate()
        );
    }
}
