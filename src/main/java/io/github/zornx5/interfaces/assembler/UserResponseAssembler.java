package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.interfaces.dto.UserResponse;
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
public class UserResponseAssembler<U extends User<U, PK>, PK extends Serializable> {

    public Page<UserResponse<U, PK>> of(Page<User<U, PK>> users) {
        return new PageImpl<>(CollectionUtils.emptyIfNull(users.getContent()).stream()
                .map(UserResponse::of)
                .collect(Collectors.toList()), users.getPageable(), users.getTotalElements());
    }
}
