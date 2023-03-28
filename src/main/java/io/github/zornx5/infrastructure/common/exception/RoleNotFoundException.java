package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 用户未找到异常
 *
 * @author zornx5
 */
public class RoleNotFoundException extends RoleException {
    public RoleNotFoundException() {
        super(ResponseStatus.ROLE_NOT_FOUND);
    }

    public RoleNotFoundException(String message) {
        super(ResponseStatus.ROLE_NOT_FOUND, message);
    }

    public RoleNotFoundException(Throwable cause) {
        super(ResponseStatus.ROLE_NOT_FOUND, cause);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(ResponseStatus.ROLE_NOT_FOUND, message, cause);
    }
}
