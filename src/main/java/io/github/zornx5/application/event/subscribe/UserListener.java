package io.github.zornx5.application.event.subscribe;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.UserDeletedEvent;
import io.github.zornx5.domain.event.UserLoggedInEvent;
import io.github.zornx5.domain.event.UserLoggedOutEvent;
import io.github.zornx5.domain.event.UserLoginFailedAttemptsIncrementedEvent;
import io.github.zornx5.domain.event.UserRegisteredEvent;
import io.github.zornx5.domain.event.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用户事件监听
 *
 * @author zornx5
 */
@Component
@Slf4j
public class UserListener<U extends User<U, PK>, PK extends Serializable> {

    @EventListener
    public void onUserRegistered(UserRegisteredEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onUserDeleted(UserDeletedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onUserUpdated(UserUpdatedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onUserLoggedIn(UserLoggedInEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onUserLoggedOut(UserLoggedOutEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onUserLoginFailedAttemptsIncremented(UserLoginFailedAttemptsIncrementedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }
}
