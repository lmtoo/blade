package cn.accessbright.blade.core.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author ll
 *
 */
public interface ExcelExporter {
	/**
	 * 导出Excel
	 * 
	 * @param request
	 * @return
	 */
	Object doExport(HttpServletRequest request, Map params) ;
}
