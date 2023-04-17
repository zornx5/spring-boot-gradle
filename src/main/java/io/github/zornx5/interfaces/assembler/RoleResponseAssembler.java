package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.interfaces.dto.RoleResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * 用户响应组装器
 *
 * @author zornx5
 */
public class RoleResponseAssembler<U extends User<U, PK>, PK extends Serializable> {

    public Page<RoleResponse<PK>> of(Page<Role<U, PK>> roles) {
        return new PageImpl<>(CollectionUtils.emptyIfNull(roles.getContent()).stream()
                .map(RoleResponse::of)
                .collect(Collectors.toList()), roles.getPageable(), roles.getTotalElements());
    }
}
