package io.github.zornx5.infrastructure.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户性别
 *
 * @author zornx5
 */

public enum UserGender {
    /**
     * 未知
     */
    UNKNOWN,
    /**
     * 男性
     */
    MALE,
    /**
     * 女性
     */
    FEMALE;

    public static Set<UserGender> of(Collection<String> userGenders) {
        Collection<String> emptyIfNull = Objects.isNull(userGenders) ? Collections.emptySet() : userGenders;
        return emptyIfNull.stream().map(UserGender::of).collect(Collectors.toSet());
    }

    public static UserGender of(String userGender) {
        return UserGender.valueOf(Objects.isNull(userGender) ? null : userGender.toUpperCase());
    }

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
