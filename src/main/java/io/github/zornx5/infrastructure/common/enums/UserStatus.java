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
public enum UserStatus implements Status {
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
    EXPIRED,

    /**
     * 不登陆的
     */
    NO_LOGIN;

    /**
     * 根据字符串获取对应的用户状态
     *
     * @param userStatus 字符串表示的用户状态
     * @return 对应的用户状态
     */
    public static UserStatus of(String userStatus) {
        return UserStatus.valueOf(Objects.isNull(userStatus) ? null : userStatus.toUpperCase());
    }

    /**
     * 根据字符串集合获取对应的用户状态集合
     *
     * @param userStatus 字符串集合表示的用户状态
     * @return 对应的用户状态集合
     */
    public static Set<UserStatus> of(Collection<String> userStatus) {
        Collection<String> emptyIfNull = Objects.isNull(userStatus) ? Collections.emptySet() : userStatus;
        return emptyIfNull.stream().map(UserStatus::of).collect(Collectors.toSet());
    }

    /**
     * 将用户状态转换为小写字符串
     *
     * @return 用户状态的小写字符串表示
     */
    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * 判断用户状态是否为活跃状态
     *
     * @return 如果用户状态为活跃状态，则返回true；否则返回false
     */
    @Override
    public boolean isActive() {
        return this.equals(UserStatus.ACTIVE);
    }

    /**
     * 获取所有的用户状态
     *
     * @return 所有的用户状态
     */
    @Override
    public Status[] getStatus() {
        return UserStatus.values();
    }
}