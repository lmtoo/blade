package cn.accessbright.blade.core.workflow.action;

import java.util.List;

import javax.servlet.ServletRequest;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.core.workflow.manager.IThreePointWorkflowManager;
import com.icitic.hrms.core.workflow.manager.WorkflowManager;

public abstract class WorkflowFinanceAction extends WorkflowAction {
	private IThreePointWorkflowManager getPointManager() throws HrmsException{
		WorkflowManager mgr = (WorkflowManager)getLocalManager();
		IThreePointWorkflowManager pointManager= (IThreePointWorkflowManager)mgr.getPointWorkflowManager();
		return pointManager;
	}
	
	/**
	 * ���������б�
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String list(User user, ServletRequest request) throws HrmsException {
		List list = getPointManager().findFinanceJobList(user.getUserId());
		request.setAttribute("list", list);
		return "list";
	}

	/**
	 * ���������ҳ��
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
	 * �ύ������
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String submit(User user, ServletRequest request) throws HrmsException {
		String jobId = getJobId(request);
		String financeStatus = request.getParameter("financeStatus");
		String financeContent = request.getParameter("financeContent");
		if (WorkflowJob.PROCESS_STATUS_UNPASS.equals(financeStatus)) {
			getPointManager().financeToBack(jobId, financeContent);
			this.showMessageDetail("�ύ�˻سɹ���");
		} else if (WorkflowJob.PROCESS_STATUS_PASS.equals(financeStatus)) {
			getPointManager().submitToFinish(jobId, financeContent);
			this.showMessageDetail("������ɹ���");
		}
		return list(user, request);
	}

	/**
	 * �����������Ϣ
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String save(User user, ServletRequest request) throws HrmsException {
		String financeStatus = request.getParameter("financeStatus");
		String financeContent = request.getParameter("financeContent");
		String jobId = getJobId(request);
		getPointManager().saveJobFinanceStatus(jobId, financeStatus, financeContent);
		this.showMessageDetail("����ɹ���");
		return list(user, request);
	}
}
