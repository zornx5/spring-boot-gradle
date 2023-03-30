package io.github.zornx5.infrastructure.repository;

/**
 * 角色搜素
 *
 * @author zornx5
 */
public interface RoleQuery {

    static RoleQuery nameOf(String name) {
        return new RoleQuery() {
            @Override
            public String getName() {
                return name;
            }
        };
    }

    default String getName() {
        return null;
    }
}
