package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.infrastructure.common.exception.ResourceNotFoundException;
import io.github.zornx5.interfaces.dto.ResourceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 资源响应组装器
 *
 * @author zornx5
 */
public class ResourceResponseAssembler<U, PK extends Serializable> {

    public Optional<ResourceResponse<U, PK>> of(Optional<Resource<U, PK>> resource) throws ResourceNotFoundException {
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return Optional.of(ResourceResponse.of(resource.get()));
    }

    public Page<ResourceResponse<U, PK>> of(Page<Resource<U, PK>> resources) {
        if (Objects.isNull(resources.getContent())) {
            return Page.empty();
        }
        return new PageImpl<>(resources.getContent().stream()
                .map(ResourceResponse::of)
                .collect(Collectors.toList()), resources.getPageable(), resources.getTotalElements());
    }
}
