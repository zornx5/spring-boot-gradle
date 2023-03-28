package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.infrastructure.common.exception.RoleNotFoundException;
import io.github.zornx5.interfaces.dto.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户响应组装器
 *
 * @author zornx5
 */
public class RoleResponseAssembler<U, PK extends Serializable> {

    public Optional<RoleResponse<U, PK>> of(Optional<Role<U, PK>> role) throws RoleNotFoundException {
        if (role.isEmpty()) {
            throw new RoleNotFoundException();
        }
        return Optional.of(RoleResponse.of(role.get()));
    }

    public Page<RoleResponse<U, PK>> of(Page<Role<U, PK>> roles) {
        if (Objects.isNull(roles.getContent())) {
            return Page.empty();
        }
        return new PageImpl<>(roles.getContent().stream()
                .map(RoleResponse::of)
                .collect(Collectors.toList()), roles.getPageable(), roles.getTotalElements());
    }
}
