package cn.accessbright.blade.core.text.typehandler;


import cn.accessbright.blade.core.Tools;

public class OrgTypeHandler extends DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		OrgBO org = SysCacheTool.findOrgById((String) value);
		if (Tools.isEmpty(param)) {
			return org;
		}
		return Tools.getPropText(org, param);
	}
}