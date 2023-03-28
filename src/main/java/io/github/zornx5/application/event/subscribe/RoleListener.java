package io.github.zornx5.application.event.subscribe;

import io.github.zornx5.domain.event.RoleDeletedEvent;
import io.github.zornx5.domain.event.RoleRegisteredEvent;
import io.github.zornx5.domain.event.RoleUpdatedEvent;
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
public class RoleListener<U, PK extends Serializable> {

    @EventListener
    public void onRoleRegistered(RoleRegisteredEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onRoleDeleted(RoleDeletedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onRoleUpdated(RoleUpdatedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }
}
