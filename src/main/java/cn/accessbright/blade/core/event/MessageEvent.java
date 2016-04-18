package cn.accessbright.blade.core.event;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.util.Tools;

/**
 * 
 * @author ll
 *
 */
public class MessageEvent extends HrEvent {
	private String message;

	public MessageEvent(Object source) {
		super(source);
	}

	public MessageEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public boolean isValid() {
		return !Tools.isEmpty(message) && (source instanceof HrmsException);
	}

	public String getMessage() {
		return message;
	}

	public HrmsException getHrmsException() {
		return (HrmsException) source;
	}
}