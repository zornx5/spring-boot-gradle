package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户响应
 *
 * @param id                  唯一标识
 * @param username            名称
 * @param firstName           名
 * @param lastName            姓
 * @param avatar              头像
 * @param description         描述
 * @param loginFailedAttempts 登陆失败次数
 * @param age                 年龄
 * @param gender              性别
 * @param email               邮件
 * @param phone               电话
 * @param address             地址
 * @param status              状态
 * @param createdBy           创建人
 * @param createdDate         创建日期
 * @param lastModifiedBy      最后修改人
 * @param lastModifiedDate    最后修改日期
 * @param <U>                 用户
 * @param <PK>                主键
 * @author zornx5
 */
public record UserResponse<U extends User<U, PK>, PK extends Serializable>(
        PK id,
        String username,
        String description,
        String firstName,
        String lastName,
        String avatar,
        Integer age,
        UserGender gender,
        String email,
        String phone,
        String address,
        UserStatus status,
        Integer loginFailedAttempts,
        Optional<U> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<U> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate,
        Optional<LocalDateTime> expiredDate,
        Collection<PK> roleIds
) {
    public static <U extends User<U, PK>, PK extends Serializable> UserResponse<U, PK> of(User<U, PK> user) {
        return new UserResponse<>(
                user.getId(),
                user.getName(),
                user.getDescription(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatar(),
                user.getAge(),
                user.getGender(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getStatus(),
                user.getLoginFailedAttempts(),
                user.getCreatedBy(),
                user.getCreatedDate(),
                user.getLastModifiedBy(),
                user.getLastModifiedDate(),
                user.getExpiredDate(),
                CollectionUtils.emptyIfNull(user.getRoles()).stream().map(Persistable::getId).collect(Collectors.toSet())
        );
    }
}
