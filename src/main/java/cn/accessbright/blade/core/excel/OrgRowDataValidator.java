package cn.accessbright.blade.core.excel;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.common.Constants;
import com.icitic.hrms.org.pojo.bo.OrgBO;

/**
 * ��֤��ʼΪ������Excel
 * 
 * @author ll
 * 
 */
public class OrgRowDataValidator implements RowDataValidator {
	protected int orgCodeIndex;
	protected int orgNameIndex;

	public OrgRowDataValidator() {
		this(0, 1);
	}

	public OrgRowDataValidator(int codeIndex, int nameIndex) {
		this.orgCodeIndex = codeIndex;
		this.orgNameIndex = nameIndex;
	}

	public final boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
		if (rowIndex > 1) {
			String orgCode = rowItem[orgCodeIndex];
			String orgName = rowItem[orgNameIndex];
			OrgBO ob = SysCacheTool.findOrgByCode(orgCode);
			if (ob == null) {
				errorInfo.add("��" + rowIndex + "�л������" + orgCode + "��ϵͳ���Ҳ���");
				return false;
			} else if (!StringUtils.equals(orgName, ob.getName())) {
				errorInfo.add("��" + rowIndex + "�л�����Ż������Ʋ�ƥ��");
				return false;
			} else if (!Constants.NO.equals(ob.getOrgCancel())) {
				errorInfo.add("��" + rowIndex + "�л���" + orgName + "�����ѱ�����");
				return false;
			}
			int beforeErrorCount = errorInfo.size();//��֤�����ֶ�֮ǰ�Ĵ�����Ϣ����
			validateNonOrgColumns(ob, rowItem, errorInfo, rowIndex);
			int afterErrorCount = errorInfo.size();//��֤�����ֶ�֮��Ĵ�����Ϣ����
			return beforeErrorCount == afterErrorCount;
		}
		return true;
	}

	/**
	 * ��֤����ķǻ�����Ϣ��
	 * 
	 * @param rowItem
	 * @param errorInfo
	 * @param rowIndex
	 * @return
	 */
	protected void validateNonOrgColumns(OrgBO ob, String[] rowItem, List errorInfo, int rowIndex) {
	}
}