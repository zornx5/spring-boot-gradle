package io.github.zornx5.infrastructure.repository;

/**
 * 角色搜素
 *
 * @author zornx5
 */
public interface RoleSearch {

    static RoleSearch nameOf(String name) {
        return new RoleSearch() {
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
