package cn.accessbright.blade.core.converter;

import com.icitic.hrms.core.event.NotifyEvent;

/**
 * 
 * @author ll
 * 
 */
public class DefaultNotifyContentConverter implements Converter {
	public Object convert(Object source) {
		if (source instanceof NotifyEvent) {
			return handleNotifyContent((NotifyEvent) source);
		}
		return source;
	}

	protected Object handleNotifyContent(NotifyEvent event) {
		return event.getTitle();
	}
}