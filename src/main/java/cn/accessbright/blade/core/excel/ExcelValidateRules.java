package cn.accessbright.blade.core.excel;

import java.util.List;

/**
 * Excel����ʱ����֤����
 * 
 * @author ll
 * 
 */
public class ExcelValidateRules implements RowDataValidator {
	private String[] fixedColumnNames;
	private int[] requiredColunmIndex;
	private RowDataValidator validator;
	private String messageSeparator = "<br>";

	/**
	 * ֻ��֤excel��ͷ���ƺͷǿ���
	 * 
	 * @param fixedColumnNames excel��ͷ����
	 * @param requiredColunmIndex �ǿ�������
	 */
	public ExcelValidateRules(String[] fixedColumnNames, int[] requiredColunmIndex) {
		this.fixedColumnNames = fixedColumnNames;
		this.requiredColunmIndex = requiredColunmIndex;
	}

	/**
	 * ��excel��ͷ���ƺͷǿ��н�����֤��ͬʱ�ṩ�Զ��������֤����ÿһ�������н�����֤
	 * 
	 * @param fixedColumnNames excel��ͷ����
	 * @param requiredColunmIndex �ǿ�������
	 * @param validator ��������֤��
	 */
	public ExcelValidateRules(String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator) {
		this(fixedColumnNames, requiredColunmIndex);
		this.validator = validator;
	}
	
	/**
	 *  ��excel��ͷ���ƺͷǿ��н�����֤��ͬʱ�ṩ�Զ��������֤����ÿһ�������н�����֤��ͬʱ֧�ֶ���֤������Ϣ�ָ������Զ���
	 *  
	 * @param fixedColumnNames excel��ͷ����
	 * @param requiredColunmIndex �ǿ�������
	 * @param validator ��������֤��
	 * @param messageSeparator ������Ϣ�ָ���
	 */
	public ExcelValidateRules(String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator,String messageSeparator) {
		this(fixedColumnNames, requiredColunmIndex,validator);
		this.messageSeparator=messageSeparator;
	}

	/**
	 * ��Excel����������֤��������ṩ��ֵ������֤Excel������
	 * 
	 * @return
	 */
	public String[] getFixedColumnNames() {
		return fixedColumnNames;
	}

	/**
	 * �ǿ��е����������н��зǿ���֤
	 * 
	 * @return
	 */
	public int[] getRequiredColunmIndex() {
		return requiredColunmIndex;
	}

	public RowDataValidator getValidator() {
		return this;
	}

	/**
	 * ������Ϣ�ָ���
	 * 
	 * @return
	 */
	public String getMessageSeparator() {
		return messageSeparator;
	}

	/**
	 * �������ͨ�����£������ṩһ��RowDataValidator��ί������֤
	 */
	public boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
		if (validator != null)
			return validator.isValidate(rowItem, errorInfo, rowIndex);
		return false;
	}
}
