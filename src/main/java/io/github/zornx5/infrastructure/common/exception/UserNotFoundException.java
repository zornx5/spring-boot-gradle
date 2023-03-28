package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 用户未找到异常
 *
 * @author zornx5
 */
public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super(ResponseStatus.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(ResponseStatus.USER_NOT_FOUND, message);
    }

    public UserNotFoundException(Throwable cause) {
        super(ResponseStatus.USER_NOT_FOUND, cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(ResponseStatus.USER_NOT_FOUND, message, cause);
    }
}
