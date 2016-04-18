package cn.accessbright.blade.core.excel;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.common.Constants;
import com.icitic.hrms.emp.pojo.bo.PersonBO;

/**
 * ��֤��ʼΪ��Ա��Excel
 * 
 * @author ll
 * 
 */
public class PersonRowDataValidator implements RowDataValidator {
	protected int personCodeIndex;
	protected int personNameIndex;

	private boolean validateCancel = true;// �Ƿ���֤��Ա���������

	public PersonRowDataValidator() {
		this(0, 1);
	}

	public PersonRowDataValidator(boolean validateCancel) {
		this();
		this.validateCancel = validateCancel;
	}

	public PersonRowDataValidator(int codeIndex, int nameIndex) {
		this.personCodeIndex = codeIndex;
		this.personNameIndex = nameIndex;
	}
	
	public PersonRowDataValidator(int codeIndex,int nameIndex,boolean validateCancel){
		this(codeIndex, nameIndex);
		this.validateCancel = validateCancel;
	}

	public boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
		if (rowIndex > 1) {
			String personCode = rowItem[personCodeIndex];
			String personName = rowItem[personNameIndex];
			PersonBO pb = SysCacheTool.findPersonByCode(personCode);
			if (pb == null) {
				errorInfo.add("��" + rowIndex + "�й���" + personCode + "��ϵͳ���Ҳ���");
				return false;
			} else if (!StringUtils.equals(personName, pb.getName())) {
				errorInfo.add("��" + rowIndex + "�й���������ƥ��");
				return false;
			}

			if (validateCancel) {
				if (!Constants.NO.equals(pb.getPersonCancel())) {
					errorInfo.add("��" + rowIndex + "��Ա��" + personName + "�����ѱ���Ա");
					return false;
				} else if (!Constants.NO.equals(pb.getRetireCancel())) {
					errorInfo.add("��" + rowIndex + "��Ա��" + personName + "����������");
					return false;
				}
			}

			int beforeErrorCount = errorInfo.size();// ��֤�����ֶ�֮ǰ�Ĵ�����Ϣ����
			validatePersonOtherAspect(pb, rowItem, errorInfo, rowIndex);
			validateNonPersonColumns(pb, rowItem, errorInfo, rowIndex);
			int afterErrorCount = errorInfo.size();// ��֤�����ֶ�֮��Ĵ�����Ϣ����
			return beforeErrorCount == afterErrorCount;
		}
		return true;
	}

	/**
	 * ��֤��Ա����������
	 * 
	 * @param pb
	 * @param rowItem
	 * @param errorInfo
	 * @param rowIndex
	 */
	protected void validatePersonOtherAspect(PersonBO pb, String[] rowItem, List errorInfo, int rowIndex) {

	}

	/**
	 * ��֤����ķ���Ա��Ϣ��
	 * 
	 * @param rowItem
	 * @param errorInfo
	 * @param rowIndex
	 * @return
	 */
	protected void validateNonPersonColumns(PersonBO pb, String[] rowItem, List errorInfo, int rowIndex) {
	}
}