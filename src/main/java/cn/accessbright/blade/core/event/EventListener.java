package cn.accessbright.blade.core.event;

/**
 * ������Դ�¼�������
 * 
 * @author ll
 * 
 */
public interface EventListener extends java.util.EventListener {
	void onEvent(DomainEvent event);
}