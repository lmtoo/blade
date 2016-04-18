package cn.accessbright.blade.core.text.typehandler;


import cn.accessbright.blade.core.Tools;

public class PersonTypeHandler extends DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		PersonBO person = SysCacheTool.findPersonById((String) value);
		if (Tools.isEmpty(param)) {
			return person;
		}
		return Tools.getPropText(person, param);
	}
}