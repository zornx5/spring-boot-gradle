package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record RoleUpdateRequest<U, PK extends Serializable>(
        @NotBlank(message = "描述不能为空") String description
) {
    public Role<U, PK> assignTo(Role<U, PK> role) {
        return role.toBuilder()
                .description(this.description)
                .build();
    }
}