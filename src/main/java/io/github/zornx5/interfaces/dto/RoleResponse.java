package io.github.zornx5.interfaces.dto;

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
 * @param <PK>             主键
 * @author zornx5
 */
public record RoleResponse<PK extends Serializable>(
        PK id,
        String name,
        String description,
        Optional<NamedResponse<PK>> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<NamedResponse<PK>> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate,
        Collection<NamedResponse<PK>> users,
        Collection<NamedResponse<PK>> resources
) {
    public static <U extends User<U, PK>, PK extends Serializable> RoleResponse<PK> of(Role<U, PK> role) {
        return new RoleResponse<>(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getCreatedBy().map(NamedResponse::of),
                role.getCreatedDate(),
                role.getLastModifiedBy().map(NamedResponse::of),
                role.getLastModifiedDate(),
                CollectionUtils.emptyIfNull(role.getUsers()).stream().map(NamedResponse::of).collect(Collectors.toSet()),
                CollectionUtils.emptyIfNull(role.getResources()).stream().map(NamedResponse::of).collect(Collectors.toSet())
        );
    }
}
