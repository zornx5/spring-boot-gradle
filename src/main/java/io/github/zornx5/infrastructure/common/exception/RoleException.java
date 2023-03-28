package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 角色异常
 *
 * @author zornx5
 */
public class RoleException extends BaseException {
    public RoleException(ResponseStatus status) {
        super(status);
    }

    public RoleException(ResponseStatus status, String message) {
        super(status, message);
    }

    public RoleException(ResponseStatus status, Throwable cause) {
        super(status, cause);
    }

    public RoleException(ResponseStatus status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
