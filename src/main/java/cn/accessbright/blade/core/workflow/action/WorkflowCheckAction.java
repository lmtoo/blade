package cn.accessbright.blade.core.workflow.action;

import java.util.List;

import javax.servlet.ServletRequest;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.core.workflow.manager.ITwoPointWorkflowManager;
import com.icitic.hrms.core.workflow.manager.WorkflowManager;

/**
 * ���������˳�����
 * 
 * @author ll
 * 
 */
public abstract class WorkflowCheckAction extends WorkflowAction {
	private ITwoPointWorkflowManager getPointManager() throws HrmsException{
		WorkflowManager mgr = (WorkflowManager)getLocalManager();
		ITwoPointWorkflowManager pointManager= (ITwoPointWorkflowManager)mgr.getPointWorkflowManager();
		return pointManager;
	}
	
	/**
	 * �������б�
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String list(User user, ServletRequest request) throws HrmsException {
		List list = getPointManager().findCheckJobList(user.getUserId());
		request.setAttribute("list", list);
		return "list";
	}

	/**
	 * ���򸴺�ҳ��
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
	 * �ύ����
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String submit(User user, ServletRequest request) throws HrmsException {
		String jobId = getJobId(request);
		String checkerStatus = request.getParameter("checkerStatus");
		String checkerContent = request.getParameter("checkerContent");
		if (WorkflowJob.PROCESS_STATUS_UNPASS.equals(checkerStatus)) {
			getPointManager().checkToBack(jobId, checkerContent);
			this.showMessageDetail("�ύ�˻سɹ���");
		} else if (WorkflowJob.PROCESS_STATUS_PASS.equals(checkerStatus)) {
			String verifierNo = request.getParameter("verifierNo");
			getPointManager().submitToVerify(jobId, checkerContent, verifierNo);
			this.showMessageDetail("�ύ�����ɹ���");
		}
		return list(user, request);
	}

	/**
	 * ���渴����Ϣ
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String save(User user, ServletRequest request) throws HrmsException {
		String checkerStatus = request.getParameter("checkerStatus");
		String checkerContent = request.getParameter("checkerContent");
		String jobId = getJobId(request);
		getPointManager().saveJobCheckStatus(jobId, checkerStatus, checkerContent);
		this.showMessageDetail("����ɹ���");
		return list(user, request);
	}
}