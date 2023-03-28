package io.github.zornx5.domain.event;

import java.io.Serializable;

/**
 * 登录失败次数增加事件接口
 *
 * @author zornx5
 */
public interface UserLoginFailedAttemptsIncrementedEvent<U, PK extends Serializable> extends UserEvent<U, PK> {
}
