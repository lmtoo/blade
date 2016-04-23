package cn.accessbright.blade.core.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * 系统事件根类
 *
 * @author ll
 */
public abstract class SystemEvent extends ApplicationEvent {

    /**
     * Create a new DomainEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public SystemEvent(Object source) {
        super(source);
    }

    public Date getDate() {
        return new Date(getTimestamp());
    }

    public abstract boolean isValid();
}