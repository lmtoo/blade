package cn.accessbright.blade.core.workflow.action;

import java.util.List;

import javax.servlet.ServletRequest;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.core.workflow.manager.WorkflowManager;

/**
 * ���������������
 * 
 * @author ll
 * 
 */
public abstract class WorkflowJobAction extends WorkflowAction {
	/**
	 * �½�����
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws HrmsException
	 */
	public String addJob(ServletRequest request, User user) throws HrmsException {
		try {
			doAddJob(request, user);
			return "javaScript";
		} catch (HrmsException e) {
			showMessageDetail(e);
			return toAdd(request, user);
		}
	}

	/**
	 * ִ������
	 * 
	 * @param request
	 * @param user
	 * @throws HrmsException
	 */
	protected abstract void doAddJob(ServletRequest request, User user) throws HrmsException;

	/**
	 * �ض������ҳ��
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws HrmsException
	 */
	public abstract String toAdd(ServletRequest request, User user) throws HrmsException;

	/**
	 * ��ѯ�����б�
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws HrmsException
	 */
	public String list(ServletRequest request, User user) throws HrmsException {
		String startDate = (String) request.getParameter("startDate");
		String endDate = (String) request.getParameter("endDate");
		WorkflowManager manager = (WorkflowManager) getLocalManager();
		List list = manager.queryJobList(user.getUserId(), startDate, endDate);
		request.setAttribute("list", list);
		request.setAttribute("userId", user.getUserId());
		return "list";
	}

	/**
	 * ɾ������
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws HrmsException
	 */
	public String delJob(ServletRequest request, User user) throws HrmsException {
		WorkflowManager manager = (WorkflowManager) getLocalManager();
		try {
			manager.deleteJob(getJobId(request));
			showMessageDetail("ɾ��" + manager.getModuleName() + "����ɹ���");
		} catch (HrmsException e) {
			showMessageDetail(e);
		}
		return list(request, user);
	}
}