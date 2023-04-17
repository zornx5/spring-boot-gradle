package io.github.zornx5.interfaces.facade;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.repository.RoleQuery;
import io.github.zornx5.interfaces.dto.RoleRegistrationRequest;
import io.github.zornx5.interfaces.dto.RoleResponse;
import io.github.zornx5.interfaces.dto.RoleUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 角色接口
 *
 * @author zornx5
 */
public interface RoleApi<U extends User<U, PK>, PK extends Serializable> {
    /**
     * 分页获取角色列表集合
     *
     * @param query    角色查询对象
     * @param pageable 分页对象
     * @return 符合条件的角色列表集合
     */
    Page<RoleResponse<PK>> page(RoleQuery query, Pageable pageable);

    /**
     * 获取角色
     *
     * @param id 角色唯一标识
     * @return 符合条件的角色
     */
    Optional<RoleResponse<PK>> get(Long id);

    /**
     * 注册角色
     *
     * @param request 角色注册请求
     * @return 注册的角色
     */
    RoleResponse<PK> register(RoleRegistrationRequest<U, PK> request);

    /**
     * 更新角色
     *
     * @param request 更新角色请求
     * @return 更新后的数据
     */
    RoleResponse<PK> update(Long id, RoleUpdateRequest<U, PK> request);

    /**
     * 删除角色
     *
     * @param id 角色唯一标识
     */
    void delete(Long id);
}
