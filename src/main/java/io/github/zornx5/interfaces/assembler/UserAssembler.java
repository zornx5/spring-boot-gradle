package io.github.zornx5.interfaces.assembler;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.interfaces.dto.UserRegistrationRequest;
import io.github.zornx5.interfaces.dto.UserUpdateRequest;

import java.io.Serializable;
import java.util.Optional;

/**
 * 用户组装器
 *
 * @author zornx5
 */
public class UserAssembler<U, PK extends Serializable> {
    public User<U, PK> of(Optional<User<U, PK>> user, UserRegistrationRequest<U, PK> request) {
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return this.of(user.get(), request);
    }

    public User<U, PK> of(User<U, PK> user, UserRegistrationRequest<U, PK> request) {
        user.setName(request.username());
        user.setDescription(request.description());
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setAvatar(request.avatar());
        user.setAge(request.age());
        user.setGender(request.gender());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setAddress(request.address());
        user.setStatus(request.status());
        user.setExpiredDate(request.expiredDate());
//        user.setRoles(request.roleIds().stream().map(JpaRole::new).collect(Collectors.toSet()));
        return user;
    }

    public User<U, PK> of(Optional<User<U, PK>> user, UserUpdateRequest<U, PK> request) {
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return this.of(user.get(), request);
    }

    public User<U, PK> of(User<U, PK> user, UserUpdateRequest<U, PK> request) {
        user.setDescription(request.description());
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setAvatar(request.avatar());
        user.setAge(request.age());
        user.setGender(request.gender());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setAddress(request.address());
        return user;
    }
}
