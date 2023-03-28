package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;
import lombok.Getter;

/**
 * 基础异常
 *
 * @author zornx5
 */
@Getter
public class BaseException extends RuntimeException {
    private final ResponseStatus status;

    public BaseException(ResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public BaseException(ResponseStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(ResponseStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public BaseException(ResponseStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
