package cn.accessbright.blade.core.text.typehandler;

import cn.accessbright.blade.core.Tools;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DecimalFormat;


public class DoubleTypeHandler extends DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		int digits = NumberUtils.toInt(param, 0);
		String suffix = Tools.generateRepeatChars(digits, '0');
		String format = "#,##0" + (suffix.length() > 0 ? ("." + suffix) : "");
		String litral = Tools.filterNullToStr(value);
		litral = litral.replaceAll(",", "");
		return new DecimalFormat(format).format(NumberUtils.toDouble(litral, 0));
	}
}