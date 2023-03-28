package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 用户未找到异常
 *
 * @author zornx5
 */
public class ResourceNotFoundException extends ResourceException {
    public ResourceNotFoundException() {
        super(ResponseStatus.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(ResponseStatus.RESOURCE_NOT_FOUND, message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(ResponseStatus.RESOURCE_NOT_FOUND, cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(ResponseStatus.RESOURCE_NOT_FOUND, message, cause);
    }
}
