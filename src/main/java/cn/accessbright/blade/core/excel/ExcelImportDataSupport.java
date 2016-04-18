package cn.accessbright.blade.core.excel;

import java.util.List;
import java.util.Map;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.event.EventPublisher;
import com.icitic.hrms.core.util.Pair;

/**
 * Excel��������֧����<br>
 * FileGenerator��generateFile����
 *  ��ȡ���Ӧ�ø�·��λ�õĵ���ģ��<br>
 * �������ֵ��/��ͷ���ʾ�����ľ���·���������ʾ���ɵ��ļ���
 * 
 * @author ll
 * 
 */
public interface ExcelImportDataSupport extends EventPublisher{
	/**
	 * ��ȡ����ҳ���title�ͱ�ע
	 * 
	 * @return
	 */
	Pair getPageTitleAndNote(Map params);

	/**
	 * ��ȡ��֤����
	 * 
	 * @param params
	 * @return
	 */
	ExcelValidateRules getExcelValidateRules(Map params) throws HrmsException;

	/**
	 * ���յĵ������ݹ���
	 * 
	 * @param importedData
	 *            ����manager�������������
	 * @param params
	 *            ������ҳ��Ĳ���
	 * @throws HrmsException
	 */
	void doBatchImport(Map params, List importedData) throws HrmsException;
	
	
	/**
	 * ����ģ���ļ�<br>
	 * �������ֵ��/��ͷ���ʾ�����ľ���·���������ʾ����baseDirĿ¼���ɵ��ļ���
	 * 
	 * @param params
	 * @param baseDir
	 * @return
	 * @throws HrmsException
	 */
	String doGenerateTemplate(Map params, String baseDir) throws HrmsException;
}