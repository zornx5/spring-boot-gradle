package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色更新请求
 *
 * @param description 描述
 * @param <U>         用户
 * @param <PK>        主键
 * @author zornx5
 */
public record RoleUpdateRequest<U, PK extends Serializable>(
        @NotBlank(message = "描述不能为空") String description
) {
    public Role<U, PK> assignTo(Role<U, PK> role) {
        return role.toBuilder()
                .description(this.description)
                .build();
    }
}
