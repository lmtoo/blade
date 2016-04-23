package cn.accessbright.blade.core.text;

import cn.accessbright.blade.core.utils.Numbers;

public class IntTypeHandler extends DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		return (int) Numbers.parseDouble(value+"",0);
	}
}