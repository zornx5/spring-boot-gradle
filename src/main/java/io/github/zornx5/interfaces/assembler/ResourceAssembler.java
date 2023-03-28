package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.exception.ResourceNotFoundException;
import io.github.zornx5.interfaces.dto.ResourceRegistrationRequest;
import io.github.zornx5.interfaces.dto.ResourceUpdateRequest;

import java.io.Serializable;
import java.util.Optional;

/**
 * 资源组装器
 *
 * @author zornx5
 */
public class ResourceAssembler<U, PK extends Serializable> {
    public Resource<U, PK> of(Optional<Resource<U, PK>> resource, ResourceRegistrationRequest<U, PK> request) {
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return this.of(resource.get(), request);
    }

    public Resource<U, PK> of(Resource<U, PK> resource, ResourceRegistrationRequest<U, PK> request) {
        resource.setName(request.name());
        resource.setDescription(request.description());
        resource.setType(request.type());
        resource.setPermission(request.permission());
        resource.setIcon(request.icon());
        resource.setUrl(request.url());
        return resource;
    }

    public Resource<U, PK> of(Optional<Resource<U, PK>> resource, ResourceUpdateRequest<U, PK> request) {
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return this.of(resource.get(), request);
    }

    public Resource<U, PK> of(Resource<U, PK> resource, ResourceUpdateRequest<U, PK> request) {
        resource.setDescription(request.description());
        resource.setType(request.type());
        resource.setPermission(request.permission());
        resource.setIcon(request.icon());
        resource.setUrl(request.url());
        return resource;
    }
}
