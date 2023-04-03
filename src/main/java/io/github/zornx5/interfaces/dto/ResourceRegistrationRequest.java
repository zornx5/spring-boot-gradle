package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.service.ResourceService;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import io.github.zornx5.infrastructure.common.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 资源注册请求
 *
 * @param name        名称
 * @param description 描述
 * @param type        类型
 * @param permission  权限
 * @param icon        图标
 * @param url         网址
 * @param <U>         用户
 * @param <PK>        主键
 * @author zornx5
 */
public record ResourceRegistrationRequest<U, PK extends Serializable>(
        @NotBlank(message = "资源名不能为空") String name,
        String description,
        @NotNull(message = "资源类型不能为空") ResourceType type,
        @NotBlank(message = "权限不能为空") String permission,
        @NotBlank(message = "图标不能为空") String icon,
        @NotBlank(message = "URL 不能为空") String url,
        Long patentId
) {
    public Resource<U, PK> assignTo(Resource<U, PK> resource, ResourceService<U, PK> resourceService) {
        return resource.toBuilder()
                .name(this.name)
                .description(this.description)
                .type(this.type)
                .permission(this.permission)
                .icon(this.icon)
                .url(this.url)
                .parent(patentId == null ? null : resourceService.findById((PK) patentId)
                        .orElseThrow(() -> new ResourceNotFoundException("不存在要关联的资源 " + patentId)))
                .build();
    }
}
