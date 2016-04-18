package cn.accessbright.blade.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.util.Tools;

/**
 * 将查询结果处理为
 * Map-->
 * key(主键):String;
 * value(数据行):String[]
 * <br>
 *
 * @see 注意：查询语句中的key，必须在第一位
 *
 * @author ll
 *
 */
public class MapToArrayResultCallBack extends QueryResultCallBack {
	private String keyColumnName;
	private Map data = new LinkedHashMap();

	public MapToArrayResultCallBack(String keyColumnName) {
		this.keyColumnName = keyColumnName;
	}

	private transient Object theKeyValue;

	public void read(int columIndex, String columnName, Object value) {
		if (StringUtils.equalsIgnoreCase(keyColumnName, columnName)) {
			theKeyValue = value;
		}
		String[] theValue = null;
		if (data.containsKey(theKeyValue)) {
			theValue = (String[]) data.get(theKeyValue);
		} else {
			theValue = new String[columnCount];
			data.put(theKeyValue, theValue);
		}
		if (theValue != null) {
			theValue[columIndex] = Tools.toString(value);
		}
	}

	public Map getData() {
		return data;
	}
}