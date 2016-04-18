package cn.accessbright.blade.core.workflow.action;

import java.util.List;

import javax.servlet.ServletRequest;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.core.workflow.manager.IOnePointWorkflowManager;
import com.icitic.hrms.core.workflow.manager.WorkflowManager;

/**
 * ��������˳�����
 * 
 * @author ll
 * 
 */
public abstract class WorkflowVerifyAction extends WorkflowAction {
	protected IOnePointWorkflowManager getPointManager() throws HrmsException{
		WorkflowManager mgr = (WorkflowManager)getLocalManager();
		IOnePointWorkflowManager pointManager= (IOnePointWorkflowManager)mgr.getPointWorkflowManager();
		return pointManager;
	}

	/**
	 * ������б�
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String list(User user, ServletRequest request) throws HrmsException {
		List list = getPointManager().findVerifyJobList(user.getUserId());
		request.setAttribute("list", list);
		return "list";
	}

	/**
	 * �������ҳ��
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String toReview(ServletRequest request) throws HrmsException {
		String jobId = getJobId(request);
		request.setAttribute("job", getPointManager().findJob(jobId));
		return "toReview";
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String save(User user, ServletRequest request) throws HrmsException {
		String verifyStatus = request.getParameter("verifierStatus");
		String verifyContents = request.getParameter("verifierContent");
		String jobId = getJobId(request);
		getPointManager().saveJobVerifyStatus(jobId, verifyStatus, verifyContents);
		this.showMessageDetail("����ɹ���");
		return list(user, request);
	}

	/**
	 * �ύ���
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String submit(User user, ServletRequest request) throws HrmsException {
		String jobId = getJobId(request);
		String verifierStatus = request.getParameter("verifierStatus");
		String verifierContent = request.getParameter("verifierContent");
		if (WorkflowJob.PROCESS_STATUS_UNPASS.equals(verifierStatus)) {
			getPointManager().verifyToBack(jobId, verifierContent);
			this.showMessageDetail("�ύ�˻سɹ���");
		} else if (WorkflowJob.PROCESS_STATUS_PASS.equals(verifierStatus)) {
			getPointManager().submitToFinish(jobId, verifierContent);
			this.showMessageDetail("�ύ�����ɹ���");
		}
		return list(user, request);
	}
}