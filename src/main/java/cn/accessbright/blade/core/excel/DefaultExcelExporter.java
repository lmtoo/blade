package cn.accessbright.blade.core.excel;

import java.util.Map;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * 默认代理给ExcelExportDataSupport
 * 
 * @author ll
 * 
 */
public class DefaultExcelExporter extends AbstractExcelExporter {
	private ExcelExportDataSupport support;

	public DefaultExcelExporter(ExcelExportDataSupport support) {
		this.support = support;
	}

	protected String doGenerateExcel(Map params, String baseDir) {
		return support.doGenerateExportExcel(params, baseDir);
	}
}