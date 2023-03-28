package io.github.zornx5.infrastructure.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户状态
 *
 * @author zornx5
 */
public enum UserStatus {
    /**
     * 草稿的
     */
    DRAFT,
    /**
     * 活跃的
     */
    ACTIVE,
    /**
     * 锁定的
     */
    LOCKED,
    /**
     * 过期的
     */
    EXPIRED;

    public static Set<UserStatus> of(Collection<String> userGenders) {
        Collection<String> emptyIfNull = Objects.isNull(userGenders) ? Collections.emptySet() : userGenders;
        return emptyIfNull.stream().map(UserStatus::of).collect(Collectors.toSet());
    }

    public static UserStatus of(String userGender) {
        return UserStatus.valueOf(Objects.isNull(userGender) ? null : userGender.toUpperCase());
    }

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
