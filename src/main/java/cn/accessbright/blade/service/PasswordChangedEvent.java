package cn.accessbright.blade.service;

import org.springframework.context.ApplicationEvent;

public class PasswordChangedEvent extends ApplicationEvent {

	public PasswordChangedEvent(Object source) {
		super(source);
	}

}
