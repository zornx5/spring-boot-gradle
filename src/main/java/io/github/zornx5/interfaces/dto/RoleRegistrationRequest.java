package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record RoleRegistrationRequest<U, PK extends Serializable>(
        @NotBlank(message = "角色名不能为空") String name,
        String description
) {
    public Role<U, PK> assignTo(Role<U, PK> role) {
        return role.toBuilder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
