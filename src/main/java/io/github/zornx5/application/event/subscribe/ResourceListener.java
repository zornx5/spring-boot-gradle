package io.github.zornx5.application.event.subscribe;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.ResourceDeletedEvent;
import io.github.zornx5.domain.event.ResourceRegisteredEvent;
import io.github.zornx5.domain.event.ResourceUpdatedEvent;
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
public class ResourceListener<U extends User<U, PK>, PK extends Serializable> {

    @EventListener
    public void onResourceRegistered(ResourceRegisteredEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onResourceDeleted(ResourceDeletedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }

    @EventListener
    public void onResourceUpdated(ResourceUpdatedEvent<U, PK> event) {
        log.info("======== event ========");
        log.info(event.toString());
        log.info(event.getSource().toString());
        log.info(String.valueOf(event.getTimestamp()));
    }
}
