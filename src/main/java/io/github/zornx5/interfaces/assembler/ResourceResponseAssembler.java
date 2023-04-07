package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.interfaces.dto.ResourceResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * 资源响应组装器
 *
 * @author zornx5
 */
public class ResourceResponseAssembler<U extends User<U, PK>, PK extends Serializable> {
    public Page<ResourceResponse<U, PK>> of(Page<Resource<U, PK>> resources) {
        return new PageImpl<>(CollectionUtils.emptyIfNull(resources.getContent()).stream()
                .map(ResourceResponse::of)
                .collect(Collectors.toList()), resources.getPageable(), resources.getTotalElements());
    }
}
