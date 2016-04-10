package cn.accessbright.blade.service;

import org.springframework.context.ApplicationEvent;

public class LoginSecussesEvent extends ApplicationEvent {

	public LoginSecussesEvent(Object source) {
		super(source);
	}

}
