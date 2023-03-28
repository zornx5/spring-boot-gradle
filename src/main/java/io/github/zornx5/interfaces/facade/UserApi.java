package io.github.zornx5.interfaces.facade;

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
public interface UserApi<U, PK extends Serializable> {
    /**
     * 获取用户列表集合
     *
     * @param pageable 分页
     * @return 符合条件的用户列表集合
     */
    Page<UserResponse<U, PK>> list(Pageable pageable);

    /**
     * 获取用户
     *
     * @param id 唯一标识
     * @return 符合条件的用户
     */
    Optional<UserResponse<U, PK>> get(String id);

    /**
     * 注册用户
     *
     * @param request 注册请求
     * @return 注册的用户
     */
    UserResponse<U, PK> register(UserRegistrationRequest<U, PK> request);

    /**
     * 修改当前用户密码
     *
     * @param password 密码
     * @return 修改后的密码
     */
    String changePassword(String password);

    /**
     * 更新用户
     *
     * @param request 更新用户请求
     * @return 更新后的数据
     */
    UserResponse<U, PK> update(String id, UserUpdateRequest<U, PK> request);

    /**
     * 删除用户
     *
     * @param id 唯一标识
     * @return 是否删除
     */
    Void delete(String id);
}
