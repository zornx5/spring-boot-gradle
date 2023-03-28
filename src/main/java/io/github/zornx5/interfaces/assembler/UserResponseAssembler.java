package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.interfaces.dto.UserResponse;
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
public class UserResponseAssembler<U, PK extends Serializable> {

    public Optional<UserResponse<U, PK>> of(Optional<User<U, PK>> user) throws UserNotFoundException {
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return Optional.of(UserResponse.of(user.get()));
    }

    public Page<UserResponse<U, PK>> of(Page<User<U, PK>> users) {
        if (Objects.isNull(users.getContent())) {
            return Page.empty();
        }
        return new PageImpl<>(users.getContent().stream()
                .map(UserResponse::of)
                .collect(Collectors.toList()), users.getPageable(), users.getTotalElements());
    }
}
