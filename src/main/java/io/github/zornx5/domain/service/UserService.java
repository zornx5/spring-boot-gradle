package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.repository.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 *
 * @author zornx5
 */
public interface UserService<U, PK extends Serializable> {

    /**
     * 创建一个实体对象，仅创建
     *
     * @return 创建的实体对象
     */
    User<U, PK> create();

    /**
     * 创建一个实体对象，仅创建
     *
     * @param id 实体唯一标识
     * @return 创建的实体对象
     */
    User<U, PK> create(String id);

    /**
     * 存储一个实体对象
     *
     * @param entity 实体对象
     * @return 存储的实体对象
     */
    User<U, PK> save(User<U, PK> entity);

    /**
     * 删除一个实体对象
     *
     * @param entity 实体对象
     */
    void delete(User<U, PK> entity);

    /**
     * 删除一个实体对象
     *
     * @param id 实体唯一标识
     */
    void delete(String id);

    /**
     * 更新一个实体对象
     *
     * @param entity 实体对象
     * @return 存储的实体对象
     */
    User<U, PK> update(User<U, PK> entity);

    /**
     * 查找一个实体对象
     *
     * @param id 实体唯一标识
     * @return 存储的实体对象
     */
    Optional<User<U, PK>> findById(String id);

    /**
     * 查找一个实体对象
     *
     * @param search 实体搜索
     * @return 查找的实体对象
     */
    Optional<User<U, PK>> findBySearch(UserSearch search);

    /**
     * 查找实体对象集合
     *
     * @param ids 实体唯一标识集合
     * @return 存储的实体对象集合
     */
    List<User<U, PK>> findAllById(Collection<String> ids);

    /**
     * 查找实体对象集合
     *
     * @param search   实体搜索
     * @param pageable 分页
     * @return 分页的实体对象集合
     */
    Page<User<U, PK>> findAll(UserSearch search, Pageable pageable);

}
