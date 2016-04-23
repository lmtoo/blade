package cn.accessbright.blade.core.text;


import cn.accessbright.blade.core.utils.Strings;

class DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		return Strings.toString(value);
	}
}