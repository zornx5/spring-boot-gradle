package io.github.zornx5.domain.entity;

import org.springframework.data.domain.Persistable;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * 可过期的
 *
 * @author zornx5
 */
public interface Expirable<ID, T extends TemporalAccessor> extends Persistable<ID> {

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    Optional<T> getExpiredDate();

    /**
     * 设置过期时间
     *
     * @param expiredDate 过期时间
     */
    void setExpiredDate(T expiredDate);
}
