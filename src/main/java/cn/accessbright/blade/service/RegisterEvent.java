package cn.accessbright.blade.service;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

	public RegisterEvent(Object source) {
		super(source);
	}

}
