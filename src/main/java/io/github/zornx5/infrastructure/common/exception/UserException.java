package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 用户异常
 *
 * @author zornx5
 */
public class UserException extends BaseException {

    public UserException(ResponseStatus status) {
        super(status);
    }

    public UserException(ResponseStatus status, String message) {
        super(status, message);
    }

    public UserException(ResponseStatus status, Throwable cause) {
        super(status, cause);
    }

    public UserException(ResponseStatus status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
