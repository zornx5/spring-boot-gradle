package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 用户已存在异常
 *
 * @author zornx5
 */
public class UserExistException extends UserException {
    public UserExistException() {
        super(ResponseStatus.USER_EXIST);
    }

    public UserExistException(String message) {
        super(ResponseStatus.USER_EXIST, message);
    }

    public UserExistException(Throwable cause) {
        super(ResponseStatus.USER_EXIST, cause);
    }

    public UserExistException(String message, Throwable cause) {
        super(ResponseStatus.USER_EXIST, message, cause);
    }
}
