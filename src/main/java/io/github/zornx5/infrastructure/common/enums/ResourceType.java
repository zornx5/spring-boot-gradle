package io.github.zornx5.infrastructure.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 枚举类，表示资源类型
 *
 * @author zornx5
 */
public enum ResourceType {
    /**
     * 未知类型
     */
    UNKNOWN,
    /**
     * 菜单类型
     */
    MENU,
    /**
     * 按钮类型
     */
    BUTTON;

    /**
     * 根据字符串返回相应的枚举值
     *
     * @param resourceType 字符串
     * @return 相应的枚举值
     */
    public static ResourceType of(String resourceType) {
        return ResourceType.valueOf(Objects.isNull(resourceType) ? null : resourceType.toUpperCase());
    }

    /**
     * 根据字符串集合返回相应的枚举值集合
     *
     * @param resourceTypes 字符串集合
     * @return 相应的枚举值集合
     */
    public static Set<ResourceType> of(Collection<String> resourceTypes) {
        Collection<String> emptyIfNull = Objects.isNull(resourceTypes) ? Collections.emptySet() : resourceTypes;
        return emptyIfNull.stream().map(ResourceType::of).collect(Collectors.toSet());
    }

    /**
     * 将枚举值转换为小写字符串
     *
     * @return 小写字符串
     */
    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
