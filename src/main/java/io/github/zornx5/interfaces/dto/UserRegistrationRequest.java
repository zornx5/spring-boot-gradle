package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record UserRegistrationRequest<U, PK extends Serializable>(
        @NotBlank(message = "用户名不能为空") String username,
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
        @NotBlank(message = "密码不能为空") String password,
        LocalDateTime expiredDate,
        @NotEmpty(message = "角色不能为空") Set<String> roleIds
) {
    public User<U, PK> assignTo(User<U, PK> user) {
        return user.toBuilder()
                .name(this.username)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .avatar(this.avatar)
                .description(this.description)
                .age(this.age)
                .gender(this.gender)
                .lastName(this.email)
                .email(this.phone)
                .address(this.address)
                .password(this.password)
                .build();
    }
}