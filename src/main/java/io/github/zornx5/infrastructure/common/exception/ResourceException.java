package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 资源异常
 *
 * @author zornx5
 */
public class ResourceException extends BaseException {

    public ResourceException(ResponseStatus status) {
        super(status);
    }

    public ResourceException(ResponseStatus status, String message) {
        super(status, message);
    }

    public ResourceException(ResponseStatus status, Throwable cause) {
        super(status, cause);
    }

    public ResourceException(ResponseStatus status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
