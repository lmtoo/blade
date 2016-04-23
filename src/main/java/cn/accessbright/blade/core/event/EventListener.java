package cn.accessbright.blade.core.event;

import org.springframework.context.ApplicationListener;

/**
 * 系统事件监听器
 * 
 * @author ll
 * 
 */
public interface EventListener<T extends SystemEvent> extends ApplicationListener<T> {
}