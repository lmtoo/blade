package cn.accessbright.blade.core.text;


public class DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		return KqPeriodTime.Tools.filterNullToStr(value);
	}
}