package io.github.zornx5.infrastructure.repository;

/**
 * 资源搜素
 *
 * @author zornx5
 */
public interface ResourceSearch {

    static ResourceSearch nameOf(String name) {
        return new ResourceSearch() {
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
