package io.github.zornx5.infrastructure.common;

/**
 * 领域构建器接口
 *
 * @author zornx5
 */
public interface DomainBuilder<O> {
    /**
     * 领域对象构建
     *
     * @return 构建的对象
     */
    O build();

    /**
     * 转换构建器接口
     *
     * @param <T> 领域对象
     */
    interface ToBuilder<T> {
        /**
         * 转换成构建器
         *
         * @return 构建器
         */
        T toBuilder();
    }
}
