package cn.accessbright.blade.core.event;

import java.util.Date;
import java.util.EventObject;

/**
 * hrϵͳ�¼�����
 *
 * @author ll
 */
public abstract class DomainEvent extends EventObject {
    /**
     * use serialVersionUID from Spring 1.2 for interoperability
     */
    private static final long serialVersionUID = 7099057708183571937L;

    /**
     * System time when the event happened
     */
    private final long timestamp;

    /**
     * Create a new DomainEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public DomainEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Return the system time in milliseconds when the event happened.
     */
    public final long getTimestamp() {
        return this.timestamp;
    }

    public Date getDate() {
        return new Date(this.timestamp);
    }

    public abstract boolean isValid();
}