package cn.accessbright.blade.core.workflow.action;

import java.util.Map;

import javax.servlet.ServletRequest;

import com.icitic.hrms.core.action.ParamDrivenAction;
import com.icitic.hrms.util.Tools;

/**
 * ������action
 * 
 * @author ll
 * 
 */
public abstract class WorkflowAction extends ParamDrivenAction {
	/**
	 * ��ȡ����id
	 * 
	 * @param request
	 * @return
	 */
	protected String getJobId(ServletRequest request) {
		return request.getParameter("jobId");
	}

	/**
	 * ��ȡ����id
	 * 
	 * @param param
	 * @return
	 */
	protected String getJobId(Map param) {
		return Tools.toString(param.get("jobId"));
	}
}