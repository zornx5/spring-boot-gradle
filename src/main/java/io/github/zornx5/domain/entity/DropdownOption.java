package io.github.zornx5.domain.entity;


import io.github.zornx5.infrastructure.common.DomainBuilder;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * 下拉选项接口
 *
 * @author zornx5
 */
@MappedSuperclass
public interface DropdownOption extends Serializable, DomainBuilder.ToBuilder<DropdownOption.Builder> {

    /**
     * 获取唯一标识
     *
     * @return 唯一标识
     */
    Long getId();

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    void setId(Long id);

    /**
     * 获取名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 设置名称
     *
     * @param name 名称
     */
    void setName(String name);

    /**
     * 获取对应值
     *
     * @return 对应值
     */
    String getValue();

    /**
     * 设置对应值
     *
     * @param value 对应值
     */
    void setValue(String value);

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 设置描述
     *
     * @param description 描述
     */
    void setDescription(String description);

    interface Builder extends DomainBuilder<DropdownOption> {

        /**
         * 唯一标识构建
         *
         * @param id 唯一标识
         * @return 构建器
         */
        DropdownOption.Builder id(Long id);

        /**
         * 名称构建
         *
         * @param name 名称
         * @return 构建器
         */
        DropdownOption.Builder name(String name);

        /**
         * 对应值构建
         *
         * @param value 对应值
         * @return 构建器
         */
        DropdownOption.Builder value(String value);

        /**
         * 描述构建
         *
         * @param description 描述
         * @return 构建器
         */
        DropdownOption.Builder description(String description);
    }
}
