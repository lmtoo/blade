package cn.accessbright.blade.core.text.typehandler;

import cn.accessbright.blade.core.Tools;
import org.apache.commons.lang3.math.NumberUtils;

public class IntTypeHandler extends DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		return (int) NumberUtils.toDouble(Tools.filterNullToStr(value), 0) + "";
	}
}