package cn.accessbright.blade.core.domain;

import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.core.workflow.domain.AbstractWorkflowJob;
import com.icitic.hrms.core.workflow.domain.TwoPointWorkflowJob;

/**
 * ����Ĺ������¼�
 * 
 * @author ll
 * 
 */
public abstract class TwoPointWorkflowJobEvent extends WorkflowJobEvent {
	public TwoPointWorkflowJobEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected Pair getSubmiterAndContent(AbstractWorkflowJob job) {
		TwoPointWorkflowJob workflowJob = (TwoPointWorkflowJob) job;

		String status = job.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (TwoPointWorkflowJob.STATUS_CHECKING.equals(status)) {
			submiter = job.getCreater();
		} else if (TwoPointWorkflowJob.STATUS_APPROVING.equals(status)) {
			submiter = workflowJob.getChecker();
			content = workflowJob.getCheckerContent();
		} else if (TwoPointWorkflowJob.STATUS_BACK.equals(status)) {
			if (TwoPointWorkflowJob.PROCESS_STATUS_UNPASS.equals(workflowJob.getCheckerStatus())) {
				submiter = workflowJob.getChecker();
				content = workflowJob.getCheckerContent();
			} else if (TwoPointWorkflowJob.PROCESS_STATUS_UNPASS.equals(workflowJob.getVerifierStatus())) {
				submiter = workflowJob.getVerifier();
				content = workflowJob.getVerifierContent();
			}
		} else if (TwoPointWorkflowJob.STATUS_FINISH.equals(status)) {
			submiter = workflowJob.getVerifier();
			content = workflowJob.getVerifierContent();
		}
		return new Pair(submiter, content);
	}
}