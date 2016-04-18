package cn.accessbright.blade.core.text.typehandler;


import cn.accessbright.blade.core.Tools;

public class DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		return Tools.filterNullToStr(value);
	}
}