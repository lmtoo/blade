package cn.accessbright.blade.core.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.core.util.ListArrayUtil.ObjectReducer;
import com.icitic.hrms.sys.pojo.bo.CodeItemBO;
import com.icitic.hrms.util.Tools;

/**
 * ���뼯��֤��
 * 
 * @author ll
 *
 */
public class CodeItemRowDataValidator extends RowDataValidatorDecorator {
	private int[] codeSetIndex;
	private String[] codeSetIds;
	
	private Map codeSetItems=new HashMap();

	public CodeItemRowDataValidator(RowDataValidator validator,int[] index,String[] codeSetIds) {
		super(validator);
		for (int i = 0; i < codeSetIds.length; i++) {
			List items=SysCacheTool.queryCodeItemBySetId(codeSetIds[i]);
			if(Tools.isEmpty(items))throw new IllegalArgumentException("δ�ҵ�����Ϊ��"+SysCacheTool.findCodeSet(codeSetIds[i]).getSetName()+"���Ĵ��뼯������");
			codeSetItems.put(codeSetIds[i], items);
		}
		
		this.codeSetIndex=index;
		this.codeSetIds=codeSetIds;
	}

	protected final void validateNonFirstRowColumns(final String[] rowItem, List errorInfo, int rowIndex) {
		if(Tools.isNotEmpty(codeSetIndex)){
			for (int i = 0; i < codeSetIndex.length; i++) {
				final int index=codeSetIndex[i];
				if(Tools.isNotEmpty(rowItem[index])){
					String codeSetId=codeSetIds[i];
					List items=(List)codeSetItems.get(codeSetId);
					boolean contain=ListArrayUtil.contain(items, new ObjectReducer() {
						public boolean reduce(Object target) {
							CodeItemBO item=(CodeItemBO)target;
							return Tools.equals(item.getItemName(), rowItem[index]);
						}
					});
					
					if(!contain){
						errorInfo.add("��" + rowIndex + "�д��뼯��"+SysCacheTool.findCodeSet(codeSetId).getSetName()+"�������ڴ����"+rowItem[index]+"��");
					}
				}
			}
		}
		validateNonCodeItemColumns(rowItem, errorInfo, rowIndex);
	}
	
	protected void validateNonCodeItemColumns(String[] rowItem, List errorInfo, int rowIndex){
	}
}