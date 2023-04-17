package io.github.zornx5.interfaces.facade;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.repository.ResourceQuery;
import io.github.zornx5.interfaces.dto.ResourceRegistrationRequest;
import io.github.zornx5.interfaces.dto.ResourceResponse;
import io.github.zornx5.interfaces.dto.ResourceUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 资源接口
 *
 * @author zornx5
 */
public interface ResourceApi<U extends User<U, PK>, PK extends Serializable> {
    /**
     * 分页获取资源列表集合
     *
     * @param query    资源查询对象
     * @param pageable 分页对象
     * @return 符合条件的资源列表集合
     */
    Page<ResourceResponse<PK>> page(ResourceQuery query, Pageable pageable);

    /**
     * 获取资源
     *
     * @param id 资源唯一标识
     * @return 符合条件的资源
     */
    Optional<ResourceResponse<PK>> get(Long id);

    /**
     * 注册资源
     *
     * @param request 资源注册请求
     * @return 注册的资源
     */
    ResourceResponse<PK> register(ResourceRegistrationRequest<U, PK> request);

    /**
     * 更新资源
     *
     * @param request 更新资源请求
     * @return 更新后的数据
     */
    ResourceResponse<PK> update(Long id, ResourceUpdateRequest<U, PK> request);

    /**
     * 删除资源
     *
     * @param id 资源唯一标识
     */
    void delete(Long id);
}
