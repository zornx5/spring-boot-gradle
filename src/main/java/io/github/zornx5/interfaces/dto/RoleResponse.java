package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 角色响应
 *
 * @param id               唯一标识
 * @param name             名称
 * @param description      描述
 * @param createdBy        创建人
 * @param createdDate      创建日期
 * @param lastModifiedBy   最后修改人
 * @param lastModifiedDate 最后修改日期
 * @param <U>              用户
 * @param <PK>             主键
 * @author zornx5
 */
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
