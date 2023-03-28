package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 角色已存在异常
 *
 * @author zornx5
 */
public class RoleExistException extends RoleException {
    public RoleExistException() {
        super(ResponseStatus.ROLE_EXIST);
    }

    public RoleExistException(String message) {
        super(ResponseStatus.ROLE_EXIST, message);
    }

    public RoleExistException(Throwable cause) {
        super(ResponseStatus.ROLE_EXIST, cause);
    }

    public RoleExistException(String message, Throwable cause) {
        super(ResponseStatus.ROLE_EXIST, message, cause);
    }
}
