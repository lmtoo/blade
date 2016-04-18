package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.core.workflow.domain.AbstractWorkflowJob;
import com.icitic.hrms.util.Tools;

/**
 * �������¼�
 * 
 * @author ll
 * 
 */
public abstract class WorkflowJobEvent extends NotifyEvent {
	public WorkflowJobEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected AbstractWorkflowJob getWorkflowJob() {
		return (AbstractWorkflowJob) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		AbstractWorkflowJob job = getWorkflowJob();
		modle.put("��������", job.getJobName());
		modle.put("��������", job.getCreateDate());

		Pair submiterAndContent = getSubmiterAndContent(job);
		String submiter = Tools.toString(submiterAndContent.getLeft());// �ύ��
		String content = Tools.toString(submiterAndContent.getRight());// ���
		if (!Tools.isEmpty(submiter)) {
			modle.put("�ύ��", submiter);
		}

		modle.put("״̬", job.getStatusName());

		if (!Tools.isEmpty(content)) {
			modle.put("���", content);
		}
		return modle;
	}

	public String getTitle() {
		return MessageFormat.format(getTitleTemplate(), new Object[] { moduleName(), message });
	}
	
	public String getJobId() {
		return getWorkflowJob().getJobId();
	}

	protected abstract Pair getSubmiterAndContent(AbstractWorkflowJob job);

	protected abstract String moduleName();
}
