package cn.accessbright.blade.core.excel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.cache.SysCache;
import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.common.Constants;
import com.icitic.hrms.org.pojo.bo.OrgBO;
import com.icitic.hrms.util.Tools;

/**
 * ��֤��ʼΪ������Excel
 * 
 * @author ll
 * 
 */
public class DeptRowDataValidator implements RowDataValidator {
	protected int deptCodeIndex=-1;
	protected int deptNameIndex;
	
	private transient static Map depts = Collections.EMPTY_MAP;
	
	public DeptRowDataValidator() {
		this(-1, 0);
	}

	public DeptRowDataValidator(int codeIndex, int nameIndex) {
		this.deptCodeIndex = codeIndex;
		this.deptNameIndex = nameIndex;
		if(depts.isEmpty())initFirstLevelDepts();
	}
	
	public DeptRowDataValidator(int nameIndex) {
		this(-1, nameIndex);
	}

	public final boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
		if (rowIndex > 1) {
			String deptCode =deptCodeIndex<0?null: rowItem[deptCodeIndex];
			String deptName = rowItem[deptNameIndex];
			OrgBO ob = (OrgBO)depts.get(deptName);
			if (ob == null) {
				errorInfo.add("��" + rowIndex + "�в�������" + deptName + "��ϵͳ���Ҳ���");
				return false;
			} else if (deptCode!=null&&!StringUtils.equals(deptCode, ob.getOrgId())) {
				errorInfo.add("��" + rowIndex + "�в��ű�Ų������Ʋ�ƥ��");
				return false;
			} else if (!Constants.NO.equals(ob.getOrgCancel())) {
				errorInfo.add("��" + rowIndex + "�в���" + deptName + "�����ѱ�����");
				return false;
			}
			
			int beforeErrorCount = errorInfo.size();//��֤�����ֶ�֮ǰ�Ĵ�����Ϣ����
			validateNonDeptColumns(ob, rowItem, errorInfo, rowIndex);
			int afterErrorCount = errorInfo.size();//��֤�����ֶ�֮��Ĵ�����Ϣ����
			return beforeErrorCount == afterErrorCount;
		}
		return true;
	}
	
	private static void initFirstLevelDepts(){
		Map deptMapper = new HashMap();
		List deptList=SysCacheTool.querySubObject(SysCache.OBJ_ORG, "", "660");
		if(Tools.isNotEmpty(deptList)){
			Iterator iter=deptList.iterator();
			while (iter.hasNext()) {
				OrgBO dept = (OrgBO) iter.next();
				if("0891000342".equals(dept.getOrgLevel())&&!Constants.YES.equals(dept.getOrgCancel()))
					deptMapper.put(dept.getName(), dept);
			}
		}
		depts=Collections.unmodifiableMap(deptMapper);//��ʼ��֮�󣬲�����ڶ��γ�ʼ��
	}
	
	public static String getDeptIdByName(String deptName){
		if(depts.isEmpty())initFirstLevelDepts();
		if(depts.containsKey(deptName)){
			OrgBO dept = (OrgBO)depts.get(deptName);
			return Tools.getPropText(dept, "pkId");
		}
		return null;
	}

	/**
	 * ��֤����ķǻ�����Ϣ��
	 * 
	 * @param rowItem
	 * @param errorInfo
	 * @param rowIndex
	 * @return
	 */
	protected void validateNonDeptColumns(OrgBO ob, String[] rowItem, List errorInfo, int rowIndex) {
	}
}