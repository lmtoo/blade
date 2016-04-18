package cn.accessbright.blade.core.action;

import java.util.List;

import com.icitic.hrms.core.excel.DefaultExcelExporter;
import com.icitic.hrms.core.excel.DefaultExcelImporter;
import com.icitic.hrms.core.excel.ExcelExportDataSupport;
import com.icitic.hrms.core.excel.ExcelImportDataSupport;
import com.icitic.hrms.core.util.Pair;

/**
 * ����Excel���뵼�����ܵ�action
 * 
 * @author ll
 * 
 */
public abstract class ExcelImportAction extends ParamDrivenAction implements ExcelImportDataSupport,ExcelExportDataSupport {
	protected void bindDelegateObjects(List deletes) {
		deletes.add(new DefaultExcelImporter(this));
		deletes.add(new DefaultExcelExporter(this));
	}

	/**
	 * ����1���򿪵���ҳ��ʱ�ı��������<br>
	 * 
	 * ��ȡ����ҳ��ı���ͱ�ע
	 * 
	 * @return
	 */
	public Pair getPageTitleAndNote() {
		return new Pair("����", "��ע");
	}
}