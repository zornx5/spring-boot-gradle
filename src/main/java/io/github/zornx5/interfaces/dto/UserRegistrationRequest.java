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

/**
 * 用户注册请求
 *
 * @param username    名称
 * @param firstName   名
 * @param lastName    姓
 * @param avatar      头像
 * @param description 描述
 * @param age         年龄
 * @param gender      性别
 * @param email       邮件
 * @param phone       电话
 * @param address     地址
 * @param status      状态
 * @param password    密码
 * @param expiredDate 过期时间
 * @param roleIds     关联角色唯一标识集合
 * @param <U>         用户
 * @param <PK>        主键
 * @author zornx5
 */
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
                .password(this.password)
                .expiredTime(this.expiredDate)
//                .roles(CollectionUtils.emptyIfNull(this.roleIds).stream().map(id->CastUtils.cast(new JpaRole(id))).collect(Collectors.toSet()))
                .build();
    }
}
