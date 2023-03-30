package io.github.zornx5.interfaces.facade;

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
public interface RoleApi<U, PK extends Serializable> {
    /**
     * 获取角色列表集合
     *
     * @param pageable 分页
     * @return 符合条件的角色列表集合
     */
    Page<RoleResponse<U, PK>> list(Pageable pageable);

    /**
     * 获取角色
     *
     * @param id 唯一标识
     * @return 符合条件的角色
     */
    Optional<RoleResponse<U, PK>> get(String id);

    /**
     * 注册角色
     *
     * @param request 注册请求
     * @return 注册的角色
     */
    RoleResponse<U, PK> register(RoleRegistrationRequest<U, PK> request);

    /**
     * 更新角色
     *
     * @param request 更新角色请求
     * @return 更新后的数据
     */
    RoleResponse<U, PK> update(String id, RoleUpdateRequest<U, PK> request);

    /**
     * 删除角色
     *
     * @param id 唯一标识
     * @return 是否删除
     */
    Void delete(String id);
}