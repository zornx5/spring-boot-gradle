package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record ResourceUpdateRequest<U, PK extends Serializable>(
        String description,
        @NotBlank(message = "资源类型不能为空") ResourceType type,
        @NotBlank(message = "权限不能为空") String permission,
        @NotBlank(message = "图标不能为空") String icon,
        @NotBlank(message = "URL 不能为空") String url
) {
    public Resource<U, PK> assignTo(Resource<U, PK> resource) {
        return resource.toBuilder()
                .description(this.description)
                .type(this.type)
                .permission(this.permission)
                .icon(this.icon)
                .url(this.url)
                .build();
    }
}