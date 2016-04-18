package cn.accessbright.blade.core.event;

/**
 * �¼�������
 * 
 * @author ll
 * 
 */
public interface EventPublisher {
	/**
	 * �����¼�
	 * 
	 * @param event
	 */
	void publishEvent(HrEvent event);
}