package cn.accessbright.blade.core.event;

import java.util.List;

/**
 * �Ӽ���Ϣ��ֱ�ӷ��ͣ�����ϵͳƫ��������
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