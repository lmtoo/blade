package cn.accessbright.blade.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询结果回调
 *
 * @author ll
 *
 */
public abstract class QueryResultCallBack {
	protected int columnCount;
	protected boolean filterSigns = true;// 是否对特殊符号进行过滤
	private Map columnAlias = new HashMap();

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	private String filterColName(String colName) {
		return filterSigns ? colName.replaceAll("[,:\\s_]+", "") : colName;
	}

	protected String getCanonicalName(String columnName) {
		if (columnAlias.containsKey(columnName)) {
			return (String) columnAlias.get(columnName);
		}
		String canonicalName = filterColName(columnName);
		columnAlias.put(columnName, canonicalName);
		return canonicalName;
	}

	public abstract void read(int columIndex, String columnName, Object value);

}
