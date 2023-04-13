package io.github.zornx5.infrastructure.repository;

import jakarta.annotation.Nullable;

/**
 * 用户搜素
 *
 * @author zornx5
 */
public interface UserQuery {

    static UserQuery nameOf(String name) {
        return new UserQuery() {
            @Override
            public String getName() {
                return name;
            }
        };
    }

    static UserQuery phoneOf(String phone) {
        return new UserQuery() {
            @Override
            public String getPhone() {
                return phone;
            }
        };
    }

    static UserQuery emailOf(String email) {
        return new UserQuery() {
            @Override
            public String getEmail() {
                return email;
            }
        };
    }

    @Nullable
    default String getName() {
        return null;
    }

    @Nullable
    default String getPhone() {
        return null;
    }

    @Nullable
    default String getEmail() {
        return null;
    }
}
