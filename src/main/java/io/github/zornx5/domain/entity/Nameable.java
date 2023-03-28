package io.github.zornx5.domain.entity;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * 可命名的
 *
 * @author zornx5
 */
public interface Nameable<PK extends Serializable> extends Persistable<PK> {

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

}
