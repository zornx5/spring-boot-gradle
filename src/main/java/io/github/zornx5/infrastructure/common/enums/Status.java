package io.github.zornx5.infrastructure.common.enums;

/**
 * 状态接口
 */
public interface Status {

    /**
     * 是否激活
     *
     * @return 如果激活则返回true，否则返回false
     */
    boolean isActive();

    /**
     * 是否未激活
     *
     * @return 如果未激活则返回true，否则返回false
     */
    default boolean isInactive() {
        return !isActive();
    }

    /**
     * 获取所有状态
     *
     * @return 所有状态
     */
    Status[] getStatus();
}