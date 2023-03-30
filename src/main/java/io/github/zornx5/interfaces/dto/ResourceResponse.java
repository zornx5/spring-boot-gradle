package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.enums.ResourceType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 资源响应
 *
 * @param id               唯一标识
 * @param name             名称
 * @param description      描述
 * @param type             类型
 * @param permission       权限
 * @param icon             图标
 * @param url              网址
 * @param createdBy        创建人
 * @param createdDate      创建日期
 * @param lastModifiedBy   最后修改人
 * @param lastModifiedDate 最后修改日期
 * @param <U>              用户
 * @param <PK>             主键
 * @author zornx5
 */
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

    public static <U, PK extends Serializable> Optional<ResourceResponse<U, PK>> of(Optional<Resource<U, PK>> resource) {
        return resource.map(ResourceResponse::of);
    }
}
