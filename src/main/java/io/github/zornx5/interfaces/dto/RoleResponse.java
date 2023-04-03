package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<LocalDateTime> lastModifiedDate,
        Collection<PK> userIds,
        Collection<PK> resourceIds
) {
    public static <U, PK extends Serializable> RoleResponse<U, PK> of(Role<U, PK> role) {
        return new RoleResponse<>(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getCreatedBy(),
                role.getCreatedDate(),
                role.getLastModifiedBy(),
                role.getLastModifiedDate(),
                CollectionUtils.emptyIfNull(role.getUsers()).stream().map(User::getId).collect(Collectors.toSet()),
                CollectionUtils.emptyIfNull(role.getResources()).stream().map(Resource::getId).collect(Collectors.toSet())
        );
    }
}
