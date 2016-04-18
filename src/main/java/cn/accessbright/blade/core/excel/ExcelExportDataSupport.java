package cn.accessbright.blade.core.excel;

import java.util.Map;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * 
 * @author ll
 *
 */
public interface ExcelExportDataSupport {
	
	/**
	 * �������ֵ��/��ͷ���ʾ�����ľ���·���������ʾ����baseDirĿ¼���ɵ��ļ���
	 * 
	 * @param params
	 * @param baseDir
	 * @return
	 * @throws HrmsException
	 */
	String doGenerateExportExcel(Map params, String baseDir) throws HrmsException;
}
