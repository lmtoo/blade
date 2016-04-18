package cn.accessbright.blade.core.excel;

import java.util.List;

/**
 * ��������֤��װ����
 * 
 * @author ll
 *
 */
public class RowDataValidatorDecorator implements RowDataValidator {
	private RowDataValidator validator;

	public RowDataValidatorDecorator(RowDataValidator validator) {
		this.validator = validator;
	}

	public boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
		if (rowIndex > 1) {
			if (validator!=null&&!validator.isValidate(rowItem, errorInfo, rowIndex))
				return false;
			int beforeErrorCount = errorInfo.size();// ��֤�����ֶ�֮ǰ�Ĵ�����Ϣ����
			validateNonFirstRowColumns(rowItem, errorInfo, rowIndex);
			int afterErrorCount = errorInfo.size();// ��֤�����ֶ�֮��Ĵ�����Ϣ����
			return beforeErrorCount == afterErrorCount;
		}
		return true;
	}

	/**
	 * ��֤����ķ���Ա��Ϣ��
	 * 
	 * @param rowItem
	 * @param errorInfo
	 * @param rowIndex
	 * @return
	 */
	protected void validateNonFirstRowColumns(String[] rowItem, List errorInfo, int rowIndex) {
	}
}