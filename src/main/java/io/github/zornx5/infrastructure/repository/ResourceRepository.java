package io.github.zornx5.infrastructure.repository;

import io.github.zornx5.domain.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 资源存储接口
 *
 * @author zornx5
 */
public interface ResourceRepository<U, PK extends Serializable> {

    /**
     * 创建一个实体对象，仅创建
     *
     * @return 创建的实体对象
     */
    Resource<U, PK> create();

    /**
     * 创建一个实体对象，仅创建
     *
     * @param id 实体唯一标识
     * @return 创建的实体对象
     */
    Resource<U, PK> create(PK id);

    /**
     * 存储一个实体对象
     *
     * @param role 实体对象
     * @return 存储的实体对象
     */
    Resource<U, PK> save(Resource<U, PK> role);

    /**
     * 删除一个实体对象
     *
     * @param role 实体对象
     */
    void delete(Resource<U, PK> role);

    /**
     * 查找一个实体对象
     *
     * @param id 实体唯一标识
     * @return 存储的实体对象
     */
    Optional<Resource<U, PK>> findById(PK id);

    /**
     * 查找一个实体对象
     *
     * @param query 实体查询对象
     * @return 查找的实体对象
     */
    Optional<Resource<U, PK>> findByQuery(ResourceQuery query);

    /**
     * 查找实体对象集合
     *
     * @param ids 实体唯一标识集合
     * @return 存储的实体对象集合
     */
    List<Resource<U, PK>> findAllById(Collection<PK> ids);

    /**
     * 查找实体对象集合
     *
     * @param query    实体查询对象
     * @param pageable 分页对象
     * @return 分页的实体对象集合
     */
    Page<Resource<U, PK>> findAll(ResourceQuery query, Pageable pageable);
}
