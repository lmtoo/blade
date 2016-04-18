package cn.accessbright.blade.core.domain;

import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.core.workflow.domain.AbstractWorkflowJob;
import com.icitic.hrms.core.workflow.domain.OnePointWorkflowJob;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;

/**
 * ����Ĺ������¼�
 * 
 * @author ll
 * 
 */
public abstract class OnePointWorkflowJobEvent extends WorkflowJobEvent {
	public OnePointWorkflowJobEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected Pair getSubmiterAndContent(AbstractWorkflowJob job) {
		OnePointWorkflowJob workflowJob = (OnePointWorkflowJob) job;
		String status = job.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���

		if (OnePointWorkflowJob.STATUS_APPROVING.equals(status)) {
			submiter = job.getCreater();
		} else if (WorkflowJob.STATUS_BACK.equals(status)) {
			if (WorkflowJob.PROCESS_STATUS_UNPASS.equals(workflowJob.getVerifierStatus())) {
				submiter = workflowJob.getVerifier();
				content = workflowJob.getVerifierContent();
			}
		} else if (WorkflowJob.STATUS_FINISH.equals(status)) {
			submiter = workflowJob.getVerifier();
			content = workflowJob.getVerifierContent();
		}
		return new Pair(submiter, content);
	}
}