package io.github.zornx5.infrastructure.repository;

import jakarta.annotation.Nullable;

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

    @Nullable
    default String getName() {
        return null;
    }
}
