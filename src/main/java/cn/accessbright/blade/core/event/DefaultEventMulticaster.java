package cn.accessbright.blade.core.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.icitic.hrms.core.mail.MailEventListener;

/**
 * Ĭ�ϵ��¼��㲥ʵ�֣�Ĭ������ʼ�������
 * 
 * @author ll
 * 
 */
public class DefaultEventMulticaster implements EventMulticaster {
	protected List listeners = new LinkedList();

	public DefaultEventMulticaster() {
		addListener(MailEventListener.getInstance());
	}

	public void addListener(EventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(EventListener listener) {
		listeners.remove(listener);
	} 

	public void removeAllListeners() {
		listeners.clear();
	}

	/**
	 * �㲥�¼�
	 */
	public void multicastEvent(HrEvent event) {
		if(event==null)return;
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			EventListener listener = (EventListener) iter.next();
			listener.onEvent(event);
		}
	}

	/**
	 * �����¼�
	 */
	public void publishEvent(HrEvent event) {
		multicastEvent(event);
	}
}