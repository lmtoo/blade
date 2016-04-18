package cn.accessbright.blade.core.excel;

import java.util.Map;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * Ĭ�ϴ����ExcelExportDataSupport
 * 
 * @author ll
 * 
 */
public class DefaultExcelExporter extends AbstractExcelExporter {
	private ExcelExportDataSupport support;

	public DefaultExcelExporter(ExcelExportDataSupport support) {
		this.support = support;
	}

	protected String doGenerateExcel(Map params, String baseDir) throws HrmsException {
		return support.doGenerateExportExcel(params, baseDir);
	}
}