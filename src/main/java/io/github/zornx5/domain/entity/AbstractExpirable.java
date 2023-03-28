package io.github.zornx5.domain.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * 过期时间的抽象基类
 *
 * @author zornx5
 */
@MappedSuperclass
public abstract class AbstractExpirable<U, PK extends Serializable> extends AbstractNameable<U, PK>
        implements Expirable<PK, LocalDateTime> {

    private static final long serialVersionUID = 14130110092L;

    @Temporal(TemporalType.TIMESTAMP)
    private @Nullable Date expiredDate;


    @Override
    public Optional<LocalDateTime> getExpiredDate() {
        return null == expiredDate ? Optional.empty()
                : Optional.of(LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}