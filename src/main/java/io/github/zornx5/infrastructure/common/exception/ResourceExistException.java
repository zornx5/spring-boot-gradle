package io.github.zornx5.infrastructure.common.exception;

import io.github.zornx5.infrastructure.common.enums.ResponseStatus;

/**
 * 角色已存在异常
 *
 * @author zornx5
 */
public class ResourceExistException extends ResourceException {
    public ResourceExistException() {
        super(ResponseStatus.RESOURCE_EXIST);
    }

    public ResourceExistException(String message) {
        super(ResponseStatus.RESOURCE_EXIST, message);
    }

    public ResourceExistException(Throwable cause) {
        super(ResponseStatus.RESOURCE_EXIST, cause);
    }

    public ResourceExistException(String message, Throwable cause) {
        super(ResponseStatus.RESOURCE_EXIST, message, cause);
    }
}
