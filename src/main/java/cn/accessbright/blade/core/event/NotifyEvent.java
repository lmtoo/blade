package cn.accessbright.blade.core.event;

import java.util.List;
import java.util.Map;

import com.icitic.hrms.core.mail.NotifyTemplate;
import com.icitic.hrms.util.Tools;

/**
 * ֪ͨ�¼�����
 * 
 * @author ll
 * 
 */
public abstract class NotifyEvent extends DomainEvent {
	private String titleTemplate = "����һ����{0}��{1}";
	protected String[] ids;
	// ֪ͨ��Ϣ
	protected Object message;

	public NotifyEvent(Object source) {
		super(source);
	}

	public NotifyEvent(String[] ids, Object source, Object message) {
		this(source);
		this.message = message;
		this.ids = ids;
	}

	public NotifyEvent(String id, Object source, Object message) {
		this(new String[] { id }, source, message);
	}

	public NotifyEvent(List ids, Object source, Object message) {
		this((String[]) ids.toArray(new String[ids.size()]), source, message);
	}

	public String[] getIds() {
		return ids;
	}

	public Object getContent() {
		return message;
	}

	public boolean isValid() {
		if (source == null)
			return false;
		if (Tools.isEmpty(ids))
			return false;
		for (int i = 0; i < ids.length; i++) {
			if (Tools.isEmpty(ids[i]))
				return false;
		}
		return true;
	}

	public String getTemplateName() {
		return NotifyTemplate.JOB_TEMPLATE;
	}

	protected String getTitleTemplate() {
		return titleTemplate + (isEndPoint() ? "��" : "������������Դϵͳ����");
	}

	public boolean isEndPoint() {
		return false;
	}

	public abstract String getJobId();

	public abstract Map getModleMap();

	public abstract String getTitle();
}