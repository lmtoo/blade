package cn.accessbright.blade.core.excel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.core.util.ListArrayUtil.ObjectCollector;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.pojo.bo.WageUnitBO;

/**
 * ��֤н�ʻ���
 * 
 * @author ll
 *
 */
public class WageUnitRowDataValidator implements RowDataValidator {
	protected int unitNameIndex;
	
	private Map allWageUnits;

	public WageUnitRowDataValidator() {
		this(0);
	}
	/**
	 * 
	 * ��ѯ���е�н�ʻ��� �浽map����   н�ʻ�������Ϊkey н�ʻ�������Ϊvalue
	 * @param nameIndex
	 */
	public WageUnitRowDataValidator(int nameIndex) {
		this.unitNameIndex = nameIndex;
		Collection wageUnits=SysCacheTool.findAllWageUnits();//��ѯ���ؼ���
		allWageUnits = (Map)ListArrayUtil.forEach(wageUnits, new ObjectCollector() {//�浽map����
			private Map unitNameToUnitMapping = new HashMap();//�����¼���
			public Object result() {
				return unitNameToUnitMapping;
			}
			public void collect(Object target) {
				WageUnitBO wageUnit=(WageUnitBO)target;
				unitNameToUnitMapping.put(wageUnit.getName(), wageUnit);
			}
		});
	}

	public final boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
		if (rowIndex > 1) {
			String unitName = rowItem[unitNameIndex];

			if (Tools.isEmpty(unitName)) {
				errorInfo.add("��" + rowIndex + "��н�ʻ������Ʋ���Ϊ��");
				return false;
			} 
			
			if (!allWageUnits.containsKey(unitName)) {
				errorInfo.add("��" + rowIndex + "��н�ʻ���������");
				return false;
			}
			
			int beforeErrorCount = errorInfo.size();// ��֤�����ֶ�֮ǰ�Ĵ�����Ϣ����
			validateNonUnitColumns((WageUnitBO)allWageUnits.get(unitName), rowItem, errorInfo, rowIndex);
			int afterErrorCount = errorInfo.size();// ��֤�����ֶ�֮��Ĵ�����Ϣ����
			return beforeErrorCount == afterErrorCount;
		}
		return true;
	}

	/**
	 * ��֤����ķ�н�ʻ�����
	 * 
	 * @param rowItem
	 * @param errorInfo
	 * @param rowIndex
	 * @return
	 */
	protected void validateNonUnitColumns(WageUnitBO ub, String[] rowItem, List errorInfo, int rowIndex) {
	}
}
