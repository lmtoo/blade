package cn.accessbright.blade.core.excel;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * 
 * @author ll
 *
 */
public abstract class AbstractExcelExporter implements ExcelExporter {
	public Object doExport(HttpServletRequest request, Map params) throws HrmsException {
		String baseDir = request.getSession().getServletContext().getRealPath("/") + File.separator + "file" + File.separator + "wage" + File.separator + "download";
		String url = request.getContextPath();
		String fileNameOrPath = doGenerateExcel(params, baseDir);
		if (fileNameOrPath != null && fileNameOrPath.startsWith("/")) {
			url += fileNameOrPath;
		} else {
			url += "/file/wage/download/" + fileNameOrPath;
		}
		request.setAttribute("url", url);
		return "download";
	}

	protected abstract String doGenerateExcel(Map params, String baseDir)  throws HrmsException;
}