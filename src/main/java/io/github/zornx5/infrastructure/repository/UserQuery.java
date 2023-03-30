package io.github.zornx5.infrastructure.repository;

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

    default String getName() {
        return null;
    }

    default String getPhone() {
        return null;
    }

    default String getEmail() {
        return null;
    }
}
