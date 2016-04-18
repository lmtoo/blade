package cn.accessbright.blade.core.workflow.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.core.excel.DefaultExcelExporter;
import com.icitic.hrms.core.excel.DefaultExcelImporter;
import com.icitic.hrms.core.excel.ExcelExportDataSupport;
import com.icitic.hrms.core.excel.ExcelImportDataSupport;
import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.core.workflow.manager.DetailedWorkflowManager;
import com.icitic.hrms.core.workflow.manager.WorkflowManager;
import com.icitic.hrms.pms.manager.UserManageManager;
import com.icitic.hrms.util.Tools;

/**
 * ������������ϸά��
 * 
 * @author ll
 * 
 */
public abstract class WorkflowJobDetailMaintinAction extends WorkflowAction implements ExcelImportDataSupport,ExcelExportDataSupport {
	protected void bindDelegateObjects(List deletes) {
		deletes.add(new DefaultExcelImporter(this));
		deletes.add(new DefaultExcelExporter(this));
	}

	/**
	 * �������ҳ��
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String toUpdate(ServletRequest request) throws HrmsException {
		String jobId = request.getParameter("jobId");
		String[] subIds = request.getParameterValues("selected_ids");
		DetailedWorkflowManager mgr = (DetailedWorkflowManager) getLocalManager();
		WorkflowJob job = mgr.findJob(jobId);
		List detailList = mgr.queryDetailByIds(subIds);
		request.setAttribute("job", job);
		request.setAttribute("detailList", detailList);
		return "toUpdate";
	}

	/**
	 * ִ����������
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String update(ServletRequest request,Map requestParams) throws HrmsException {
		DetailedWorkflowManager mgr = (DetailedWorkflowManager) getLocalManager();
		String[] paramNames=mgr.getJobDetailUpdateParamNames();
		int size = Integer.parseInt(request.getParameter("size"));
		List params = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			String[] paramValues=new String[paramNames.length];
			for (int j = 0; j < paramNames.length; j++) {
				paramValues[j]=request.getParameter(paramNames[j]+i);
			}
			params.add(paramValues);
		}
		mgr.batchUpdateDetails(params);
		return list(request,requestParams);
	}

	/**
	 * indexҳ��
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String list(ServletRequest request,Map params) throws HrmsException {
		String jobId = request.getParameter("jobId");
		request.setAttribute("jobBO", ((WorkflowManager) getLocalManager()).findJob(jobId));
		request.setAttribute("detailList", queryForDetail(params, jobId));
		return "list";
	}

	/**
	 * ����˸�����ѡ��ҳ��
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws HrmsException
	 */
	public String checkList(ServletRequest request, User user) throws HrmsException {
		String process = request.getParameter("process");
		UserManageManager manager = new UserManageManager();
		Pair menuId = getCheckAndVirifyMenuId();
		String operateID = (String) ("selCheck".equals(process) ? menuId.getLeft() : menuId.getRight());
		List list = manager.queryFlowPerson(user.getUserId(), operateID);
		request.setAttribute("list", list);
		return "checkerList";
	}

	/**
	 * �ύ����/���
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public Object submitReview(ServletRequest request,Map params) throws HrmsException {
		String jobId = request.getParameter("jobId");
		String financeId = request.getParameter("financeId");// ����/�����id
		try {
			validateJobDetailSubmitReview(jobId);
			WorkflowManager mgr = (WorkflowManager) getLocalManager();
			mgr.getPointWorkflowManager().submitToBegin(financeId, jobId);
			this.showMessageDetail("�ύ�ɹ���");
			return redirectToJobIndex();
		} catch (HrmsException e) {
			showMessageDetail(e);
			return list(request,params);
		}
	}

	/**
	 * ��֤������ϸ�Ƿ������ύ���˵�����
	 * 
	 * @param jobId
	 * @return
	 * @throws HrmsException
	 *             һ�������Ӧ������֤�����ʾ��Ϣ�������Ǵ���
	 */
	protected void validateJobDetailSubmitReview(String jobId) throws HrmsException{
	};

	/**
	 * ����Excel
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public String doGenerateExportExcel(Map params, String baseDir) throws HrmsException {
		String jobId =Tools.toString(params.get("jobId"));
		WorkflowJob job = ((WorkflowManager) getLocalManager()).findJob(jobId);
		List data = queryForDetail(params, jobId);
		return exportToExcel(baseDir, data, job);
	}

	/**
	 * ά��ҳ���ѯ������ϸ
	 * 
	 * @param request
	 * @param jobId
	 * @return
	 * @throws HrmsException
	 */
	protected abstract List queryForDetail(Map params, String jobId) throws HrmsException;

	/**
	 * �ύ����֮����Ҫ�ض����������б�
	 * 
	 * @param request
	 * @return
	 */
	protected abstract String redirectToJobIndex();


	/**
	 * ����ϸ���ݵ��뵽Excel�У�����ֵΪ���ɵ��ļ�����
	 * 
	 * @param path
	 * @param data
	 * @param job
	 * @return ���ɵ��ļ�����
	 */
	protected abstract String exportToExcel(String path, List data, WorkflowJob job)throws HrmsException;

	/**
	 * ��ȡ���˺���˲˵�id
	 * 
	 * @return Pair left:checkMenu,right:virifyMenu
	 */
	protected abstract Pair getCheckAndVirifyMenuId();
}