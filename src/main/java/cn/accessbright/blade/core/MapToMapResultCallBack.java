package cn.accessbright.blade.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.util.Tools;

/**
 * 将查询结果处理为 Map--> key(主键):String; value(数据行): Map->key(列名);value(单元格值) <br>
 *
 * @see 注意：查询语句中的key，必须在第一位
 *
 * @author ll
 *
 */
public class MapToMapResultCallBack extends QueryResultCallBack {
	private String keyColumnName;
	private Map data = new LinkedHashMap();

	public MapToMapResultCallBack(String keyColumnName) {
		this.keyColumnName = keyColumnName;
	}

	public MapToMapResultCallBack(String keyColumnName, boolean filterSigns) {
		this(keyColumnName);
		this.filterSigns = filterSigns;
	}

	private transient Object theKeyValue;

	public void read(int columIndex, String columnName, Object value) {
		if (StringUtils.equalsIgnoreCase(keyColumnName, columnName)) {
			theKeyValue = value;
		}
		Map theValue = null;
		if (data.containsKey(theKeyValue)) {
			theValue = (Map) data.get(theKeyValue);
		} else {
			theValue = new LinkedHashMap();
			data.put(theKeyValue, theValue);
		}
		if (theValue != null) {
			theValue.put(getCanonicalName(columnName), Tools.toString(value));
		}
	}

	public Map getData() {
		return data;
	}
}