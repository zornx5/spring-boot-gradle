package io.github.zornx5.infrastructure.repository;

/**
 * 用户搜素
 *
 * @author zornx5
 */
public interface UserSearch {

    static UserSearch nameOf(String name) {
        return new UserSearch() {
            @Override
            public String getName() {
                return name;
            }
        };
    }

    static UserSearch phoneOf(String phone) {
        return new UserSearch() {
            @Override
            public String getPhone() {
                return phone;
            }
        };
    }

    static UserSearch emailOf(String email) {
        return new UserSearch() {
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
