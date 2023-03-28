package io.github.zornx5.interfaces.dto;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

public record UserResponse<U, PK extends Serializable>(
        PK id,
        String username,
        String firstName,
        String lastName,
        String avatar,
        String description,
        Integer loginFailedAttempts,
        Integer age,
        UserGender gender,
        String email,
        String phone,
        String address,
        UserStatus status,
        Optional<U> createdBy,
        Optional<LocalDateTime> createdDate,
        Optional<U> lastModifiedBy,
        Optional<LocalDateTime> lastModifiedDate,
        Optional<LocalDateTime> expiredDate
) {
    public static <U, PK extends Serializable> UserResponse<U, PK> of(User<U, PK> user) {
        return new UserResponse<>(
                user.getId(),
                user.getName(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatar(),
                user.getDescription(),
                user.getLoginFailedAttempts(),
                user.getAge(),
                user.getGender(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getStatus(),
                user.getCreatedBy(),
                user.getCreatedDate(),
                user.getLastModifiedBy(),
                user.getLastModifiedDate(),
                user.getExpiredDate()
        );
    }
}
