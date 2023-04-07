package io.github.zornx5.interfaces.facade;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.interfaces.dto.UserRegistrationRequest;
import io.github.zornx5.interfaces.dto.UserResponse;
import io.github.zornx5.interfaces.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 用户接口
 *
 * @author zornx5
 */
public interface UserApi<U extends User<U, PK>, PK extends Serializable> {
    /**
     * 分页获取用户列表集合
     *
     * @param query    用户查询对象
     * @param pageable 分页对象
     * @return 符合条件的用户列表集合
     */
    Page<UserResponse<U, PK>> page(UserQuery query, Pageable pageable);

    /**
     * 获取用户
     *
     * @param id 用户唯一标识
     * @return 符合条件的用户
     */
    Optional<UserResponse<U, PK>> get(Long id);

    /**
     * 注册用户
     *
     * @param request 用户注册请求
     * @return 注册的用户
     */
    UserResponse<U, PK> register(UserRegistrationRequest<U, PK> request);

    /**
     * 更新用户
     *
     * @param request 更新用户请求
     * @return 更新后的数据
     */
    UserResponse<U, PK> update(Long id, UserUpdateRequest<U, PK> request);

    /**
     * 删除用户
     *
     * @param id 用户唯一标识
     */
    void delete(Long id);
}
