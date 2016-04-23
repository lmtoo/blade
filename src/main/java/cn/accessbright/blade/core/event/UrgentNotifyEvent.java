package cn.accessbright.blade.core.event;

import java.util.List;

/**
 *加急消息，直接发送，不管系统偏移量参数
 * 
 * @author ll
 * 
 */
public abstract class UrgentNotifyEvent extends NotifyEvent {
	public UrgentNotifyEvent(Object source) {
		super(source);
	}

	public UrgentNotifyEvent(String[] ids, Object source, Object message) {
		super(ids, source, message);
	}

	public UrgentNotifyEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	public UrgentNotifyEvent(List ids, Object source, Object message) {
		super(ids, source, message);
	}
}