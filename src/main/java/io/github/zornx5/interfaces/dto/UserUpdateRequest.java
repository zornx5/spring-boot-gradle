package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public record UserUpdateRequest<U, PK extends Serializable>(
        String description,
        @NotBlank(message = "姓不能为空") String firstName,
        @NotBlank(message = "名不能为空") String lastName,
        String avatar,
        @NotNull(message = "年龄不能为空") Integer age,
        @NotNull(message = "性别不能为空") UserGender gender,
        @NotBlank(message = "邮件不能为空") String email,
        @NotBlank(message = "手机不能为空") String phone,
        String address,
        @NotNull(message = "状态不能为空") UserStatus status,
        LocalDateTime expiredDate,
        @NotEmpty(message = "角色不能为空") Set<String> roleIds
) {
    public User<U, PK> assignTo(User<U, PK> user) {
        return user.toBuilder()
                .description(this.description)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .avatar(this.avatar)
                .age(this.age)
                .gender(this.gender)
                .email(this.email)
                .phone(this.phone)
                .address(this.address)
                .status(this.status)
                .expiredTime(this.expiredDate)
                .build();
    }

    public User<U, PK> assignTo(Optional<User<U, PK>> user) {
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return this.assignTo(user.get());
    }
}
