package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import org.apache.commons.collections4.CollectionUtils;

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
public record UserInfoResponse<U extends User<U, PK>, PK extends Serializable>(
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
        Optional<NamedResponse<PK>> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<NamedResponse<PK>> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate,
        Optional<LocalDateTime> expiredDate,
        Collection<RoleInfo<PK>> roles
) {

    public static <U extends User<U, PK>, PK extends Serializable> UserInfoResponse<U, PK> of(User<U, PK> user) {
        return new UserInfoResponse<>(
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
                user.getCreatedBy().map(NamedResponse::of),
                user.getCreatedDate(),
                user.getLastModifiedBy().map(NamedResponse::of),
                user.getLastModifiedDate(),
                user.getExpiredDate(),
                CollectionUtils.emptyIfNull(user.getRoles()).stream().map(RoleInfo::of).collect(Collectors.toSet())
        );
    }

    record RoleInfo<PK extends Serializable>(
            PK id,
            String name,
            String description,
            Collection<ResourceInfo<PK>> resources
    ) {
        public static <U extends User<U, PK>, PK extends Serializable> RoleInfo<PK> of(Role<U, PK> role) {
            return new RoleInfo<>(
                    role.getId(),
                    role.getName(),
                    role.getDescription(),
                    CollectionUtils.emptyIfNull(role.getResources()).stream().map(ResourceInfo::of).collect(Collectors.toSet())
            );
        }
    }

    record ResourceInfo<PK extends Serializable>(
            PK id,
            String name,
            ResourceType type,
            String permission,
            String icon,
            String url,
            Collection<ResourceInfo<PK>> children
    ) {
        public static <U extends User<U, PK>, PK extends Serializable> ResourceInfo<PK> of(Resource<U, PK> resource) {
            return new ResourceInfo<>(
                    resource.getId(),
                    resource.getName(),
                    resource.getType(),
                    resource.getPermission(),
                    resource.getIcon(),
                    resource.getUrl(),
                    CollectionUtils.emptyIfNull(resource.getChildren()).stream().map(ResourceInfo::of).collect(Collectors.toSet())
            );
        }
    }
}
