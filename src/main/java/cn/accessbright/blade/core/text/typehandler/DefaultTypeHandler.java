package cn.accessbright.blade.core.text.typehandler;


import cn.accessbright.blade.core.KqPeriodTime;

public class DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		return KqPeriodTime.Tools.filterNullToStr(value);
	}
}