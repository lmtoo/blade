package cn.accessbright.blade.core.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * 
 * @author ll
 *
 */
public interface ExcelExporter {
	/**
	 * ����Excel
	 * 
	 * @param request
	 * @return
	 * @throws HrmsException
	 */
	Object doExport(HttpServletRequest request, Map params) throws HrmsException;
}
