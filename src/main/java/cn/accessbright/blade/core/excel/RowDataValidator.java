package cn.accessbright.blade.core.excel;

import java.util.List;

/**
 * excel��������֤��
 * 
 * @author ll
 * 
 */
public interface RowDataValidator {
	/**
	 * �������Ƿ���Ч
	 * 
	 * @param rowItem
	 *            ��ǰ������
	 * @param errorInfo
	 *            ��Ŵ�����Ϣ���б�
	 * @param rowIndex
	 *            ��ǰ������
	 * @return
	 */
	boolean isValidate(String[] rowItem, List errorInfo, int rowIndex);
}