package io.github.zornx5.infrastructure.repository;

import javax.annotation.Nullable;

/**
 * 资源搜素
 *
 * @author zornx5
 */
public interface ResourceQuery {

    static ResourceQuery nameOf(String name) {
        return new ResourceQuery() {
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
