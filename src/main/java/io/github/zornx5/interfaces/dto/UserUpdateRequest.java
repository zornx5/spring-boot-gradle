package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.service.RoleService;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import io.github.zornx5.infrastructure.common.exception.RoleNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record UserUpdateRequest<U extends User<U, PK>, PK extends Serializable>(
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
        Set<Long> roleIds
) {
    public User<U, PK> assignTo(User<U, PK> user, RoleService<U, PK> roleService) {
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
                .roles(CollectionUtils.emptyIfNull(this.roleIds)
                        .stream()
                        .map(id -> roleService.findById((PK) id)
                                .orElseThrow(() -> new RoleNotFoundException("不存在要关联的角色" + id)))
                        .collect(Collectors.toSet()))
                .build();
    }
}
