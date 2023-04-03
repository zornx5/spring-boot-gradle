package io.github.zornx5.infrastructure.common.enums;

public interface Status {

    boolean isActive();

    default boolean isInactive() {
        return !isActive();
    }

    Status[] getStatus();
}
