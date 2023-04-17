package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
 * @param <PK>             主键
 * @author zornx5
 */
public record ResourceResponse<PK extends Serializable>(
        PK id,
        String name,
        String description,
        ResourceType type,
        String permission,
        String icon,
        String url,
        Optional<NamedResponse<PK>> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<NamedResponse<PK>> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate,
        Collection<NamedResponse<PK>> roles,
        Optional<NamedResponse<PK>> parent,
        Collection<NamedResponse<PK>> children
) {
    public static <U extends User<U, PK>, PK extends Serializable> ResourceResponse<PK> of(Resource<U, PK> resource) {
        return new ResourceResponse<>(
                resource.getId(),
                resource.getName(),
                resource.getDescription(),
                resource.getType(),
                resource.getPermission(),
                resource.getIcon(),
                resource.getUrl(),
                resource.getCreatedBy().map(NamedResponse::of),
                resource.getCreatedDate(),
                resource.getLastModifiedBy().map(NamedResponse::of),
                resource.getLastModifiedDate(),
                CollectionUtils.emptyIfNull(resource.getRoles()).stream().map(NamedResponse::of).collect(Collectors.toSet()),
                Optional.ofNullable(resource.getParent()).map(NamedResponse::of),
                CollectionUtils.emptyIfNull(resource.getChildren()).stream().map(NamedResponse::of).collect(Collectors.toSet())
        );
    }
}
