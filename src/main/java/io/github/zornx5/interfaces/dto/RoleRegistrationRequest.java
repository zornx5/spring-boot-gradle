package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.service.ResourceService;
import io.github.zornx5.infrastructure.common.exception.RoleNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

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
        String description,
        Set<Long> resourceIds
) {
    public Role<U, PK> assignTo(Role<U, PK> role, ResourceService<U, PK> resourceService) {
        return role.toBuilder()
                .name(this.name)
                .description(this.description)
                .resources(CollectionUtils.emptyIfNull(this.resourceIds)
                        .stream()
                        .map(id -> resourceService.findById((PK) id)
                                .orElseThrow(() -> new RoleNotFoundException("不存在要关联的资源 " + id)))
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
