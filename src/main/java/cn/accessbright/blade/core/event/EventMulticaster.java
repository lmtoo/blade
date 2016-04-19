package cn.accessbright.blade.core.event;

/**
 * �¼��㲥
 * 
 * @author ll
 * 
 */
public interface EventMulticaster extends EventPublisher {
	/**
	 * Add a listener to be notified of all events.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	void addListener(EventListener listener);

	/**
	 * Remove a listener from the notification list.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	void removeListener(EventListener listener);

	/**
	 * Remove all listeners registered with this multicaster.
	 * <p>
	 * After a remove call, the multicaster will perform no action on event
	 * notification until new listeners are being registered.
	 */
	void removeAllListeners();

	/**
	 * Multicast the given application event to appropriate listeners.
	 * 
	 * @param event
	 *            the event to multicast
	 */
	void multicastEvent(DomainEvent event);
}
