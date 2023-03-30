package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色注册请求
 *
 * @param name        名称
 * @param description 描述
 * @param <U>         用户
 * @param <PK>        主键
 * @author zornx5
 */
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
