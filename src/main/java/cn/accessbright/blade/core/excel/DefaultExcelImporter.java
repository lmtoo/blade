package cn.accessbright.blade.core.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.icitic.hrms.common.Constants;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.event.MessageEvent;
import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.px.pojo.ExportForm;
import com.icitic.hrms.util.Tools;

/**
 * Excel���빤��
 * 
 * @author ll
 * 
 */
public class DefaultExcelImporter implements ExcelImporter {
	private static final String DEFAULT_VIEW_IMPORT = "/jsp/common/importData.jsp";
	private static final String DEFAULT_VIEW_CALLBACK = "/jsp/common/javaScript.jsp";

	private String importView;
	private String callbackView;

	private ExcelExporter exporter;
	private ExcelImportDataSupport support;

	public DefaultExcelImporter(ExcelImportDataSupport support) {
		this(support, DEFAULT_VIEW_IMPORT, DEFAULT_VIEW_CALLBACK);
	}

	public DefaultExcelImporter(final ExcelImportDataSupport support, String importView, String callbackView) {
		this.exporter = new AbstractExcelExporter() {
			protected String doGenerateExcel(Map params, String baseDir) throws HrmsException {
				return support.doGenerateTemplate(params, baseDir);
			}
		};
		this.importView = importView;
		this.callbackView = callbackView;
		this.support = support;
	}

	/**
	 * ��ʼ������ҳ����Ϣ
	 * 
	 * @param request
	 * @param mapping
	 */
	private void initImportPage(ServletRequest request, ActionMapping mapping, Map params) {
		Pair titleAndNote = support.getPageTitleAndNote(params);
		request.setAttribute("title", titleAndNote.getLeft());
		request.setAttribute("note", titleAndNote.getRight());
		request.setAttribute("actionUrl", mapping.getPath());
	}

	/**
	 * ת������ҳ��<br>
	 * ���뵼��ҳ��ʱҪ��session�з��õ�ֵ
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	public Object toImport(HttpServletRequest request, ActionMapping mapping, Map params) throws HrmsException {
		initImportPage(request, mapping, params);
		setSessionValue(request.getSession(), params);
		exporter.doExport(request, params);
		return new ActionForward(importView);
	}

	/**
	 * ����ļ��ϴ���ť
	 * 
	 * @param session
	 * @param request
	 * @param form
	 * @return
	 * @throws HrmsException
	 */
	public Object doUpload(HttpServletRequest request, ActionMapping mapping, ActionForm form) throws HrmsException {
		HttpSession session = request.getSession();
		Map params = getSessionValue(session);
		try {
			initImportPage(request, mapping, params);
			String filepath = Tools.uploadFile(session.getServletContext(), ((ExportForm) form).getFile());
			List sourceData = readDataToListArray(filepath, params);
			session.setAttribute(Constants.ARRAY_STRING, filepath);
			request.setAttribute(Constants.OBJECT_ARRAY, sourceData);
			return new ActionForward(importView);
		} catch (HrmsException e) {
			if (e.isErrorMessage())
				throw e;// ����Ǵ�����Ϣ�����׳�
			support.publishEvent(new MessageEvent(e, e.getMessage()));
			return toImport(request, mapping, params);
		}
	}

	/**
	 * ���ִ�е��밴ť
	 * 
	 * @param session
	 * @return
	 * @throws HrmsException
	 */
	public Object doImport(HttpServletRequest request, ActionMapping mapping) throws HrmsException {
		HttpSession session = request.getSession();
		Map params = getSessionValue(session);
		try {
			String filepath = (String) session.getAttribute(Constants.ARRAY_STRING);
			List sourceData = readDataToListArray(filepath, params);
			support.doBatchImport(params, sourceData);
			return new ActionForward(callbackView);
		} catch (HrmsException e) {
			if (e.isErrorMessage())
				throw e;// ����Ǵ�����Ϣ�����׳�
			support.publishEvent(new MessageEvent(e, e.getMessage()));
			return toImport(request, mapping, params);
		}
	}

	/**
	 * ��ȡ������session�е��������ֵ
	 * 
	 * @param session
	 * @return �򿪵���ҳ��ʱ���������
	 */
	private Map getSessionValue(HttpSession session) {
		return (Map) session.getAttribute(Constants.MAP_STRING);
	}

	/**
	 * ������������õ�session��
	 * 
	 * @param session
	 * @param params
	 *            �򿪵���ҳ��ʱ���������
	 */
	private void setSessionValue(HttpSession session, Map params) {
		session.setAttribute(Constants.MAP_STRING, params);
	}

	/**
	 * ����2����ȡ�ϴ���excel�ļ���List��<br>
	 * 
	 * ��ȡExcel�ļ���һ��ListArray��
	 * 
	 * @param filepath
	 * @return
	 * @throws HrmsException
	 */
	private List readDataToListArray(String filepath, Map params) throws HrmsException {
		return ListArrayUtil.importDataToListArray(filepath, support.getExcelValidateRules(params));
	}
}