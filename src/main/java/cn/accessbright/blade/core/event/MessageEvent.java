package cn.accessbright.blade.core.event;

import cn.accessbright.blade.core.Strings;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.util.Tools;

/**
 * 
 * @author ll
 *
 */
public class MessageEvent extends DomainEvent {
	private String message;

	public MessageEvent(Object source) {
		super(source);
	}

	public MessageEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public boolean isValid() {
		return !Strings.isEmpty(message) && (source instanceof HrmsException);
	}

	public String getMessage() {
		return message;
	}

	public HrmsException getHrmsException() {
		return (HrmsException) source;
	}
}