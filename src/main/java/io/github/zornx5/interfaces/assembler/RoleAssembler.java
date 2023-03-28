package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.infrastructure.common.exception.RoleNotFoundException;
import io.github.zornx5.interfaces.dto.RoleRegistrationRequest;
import io.github.zornx5.interfaces.dto.RoleUpdateRequest;

import java.io.Serializable;
import java.util.Optional;

/**
 * 用户组装器
 *
 * @author zornx5
 */
public class RoleAssembler<U, PK extends Serializable> {
    public Role<U, PK> of(Optional<Role<U, PK>> role, RoleRegistrationRequest<U, PK> request) {
        if (role.isEmpty()) {
            throw new RoleNotFoundException();
        }
        return this.of(role.get(), request);
    }

    public Role<U, PK> of(Role<U, PK> role, RoleRegistrationRequest<U, PK> request) {
        role.setName(request.name());
        role.setDescription(request.description());
        return role;
    }

    public Role<U, PK> of(Optional<Role<U, PK>> role, RoleUpdateRequest<U, PK> request) {
        if (role.isEmpty()) {
            throw new RoleNotFoundException();
        }
        return this.of(role.get(), request);
    }

    public Role<U, PK> of(Role<U, PK> role, RoleUpdateRequest<U, PK> request) {
        role.setDescription(request.description());
        return role;
    }
}
