package cn.accessbright.blade.core.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * Excel导入工具
 *
 * @author ll
 *
 */
public interface ExcelImporter {
	/**
	 * 1、跳转到导入页面
	 *
	 * @param request
	 * @param mapping
	 * @param params
	 * @return
	 * @throws HrmsException
	 */
	Object toImport(HttpServletRequest request, ActionMapping mapping, Map params) throws HrmsException;

	/**
	 * 2、上传Excel
	 *
	 * @param request
	 * @param mapping
	 * @param form
	 * @return
	 * @throws HrmsException
	 */
	Object doUpload(HttpServletRequest request, ActionMapping mapping, ActionForm form) throws HrmsException;

	/**
	 * 3、执行导入数据
	 *
	 * @param session
	 * @return
	 * @throws HrmsException
	 */
	Object doImport(HttpServletRequest request, ActionMapping mapping) throws HrmsException;
}