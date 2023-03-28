package io.github.zornx5.infrastructure.common;

/**
 * 事件接口，需要继承以下对象
 * {@link java.util.EventObject}
 * {@link org.springframework.context.ApplicationEvent}
 *
 * @author zornx5
 */
public interface DomainEvent {

    /**
     * 获取事件来源
     *
     * @return 事件来源
     */
    Object getSource();

    /**
     * 获取事件发生时间
     *
     * @return 事件发生事件
     */
    default long getTimestamp() {
        return System.currentTimeMillis();
    }
}
