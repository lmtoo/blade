package cn.accessbright.blade.core.excel;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.icitic.hrms.common.action.GenericAction;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.util.Tools;

/**
 * Excel����Action����ǿ��Excel���빦�ܣ����Լ���Excel������ع��ܵĿ���
 * @deprecated ��ʹ��com.icitic.hrms.core.action.ExcelImportAction
 * 
 * @author ll
 * 
 */
public abstract class ExcelImportAction extends GenericAction{
	public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		String act = request.getParameter("act");
		String forward = "list";
		try {
			if ("doUpload".equals(act)) {
				ExcelImporter importer = getExcelImporter(request);
				return (ActionForward)importer.doUpload(request, mapping, form);
			} else if ("doImport".equals(act)) {
				ExcelImporter importer = getExcelImporter(request);
				return (ActionForward)importer.doImport(request, mapping);
			} else if ("toImport".equals(act)) {
				ExcelImporter importer = getExcelImporter(request);
				return (ActionForward)importer.toImport(request, mapping, getParamMap(request));
			} else {
				return doExecuteDo(user,mapping, form, request, response);
			}
		} catch (HrmsException he) {
			ActionError error = new ActionError("info", he.getFlag(), he.getMessage(), he.toString());
			this.actionErrors.add(error);
		} catch (Exception e) {
			// ��action���׳������з�HrmsException�쳣��װ��һ��HrmsException�쳣
			HrmsException he = new HrmsException(new StringBuffer().append(getClass().getName()).append("���ִ���").toString(), e, this.getClass());
			ActionError ae = new ActionError("info", he.getFlag(), he.getMessage(), he.toString());
			this.actionErrors.add(ae);
		}
		return mapping.findForward(forward);
	}
	

	/**
	 * ��ȡ�������
	 * 
	 * @param request
	 * @return
	 */
	private Map getParamMap(HttpServletRequest request) {
		Map paramMapping = new HashMap();
		Enumeration enumer = request.getParameterNames();
		while (enumer.hasMoreElements()) {
			String paramName = (String) enumer.nextElement();
			String paramValue = request.getParameter(paramName);
			if (!Tools.isEmpty(paramValue)) {
				paramMapping.put(paramName, paramValue);
			} else {
				String[] paramValues = request.getParameterValues(paramName);
				if (!Tools.isEmpty(paramValues)) {
					paramMapping.put(paramName, paramValues);
				}
			}
		}
		return paramMapping;
	}
	
	protected ExcelImporter getExcelImporter(HttpServletRequest request) {
		return new DefaultExcelImporter(getExcelImportDataSupport(request));
	}
	
	protected abstract ExcelImportDataSupport getExcelImportDataSupport(HttpServletRequest request);
	
	/**
	 * ��Excel�ļ�����action�������û�����ʣ���ִ�ж���
	 * 
	 * @param user
	 * @param session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected abstract ActionForward doExecuteDo(User user,ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws HrmsException;
}