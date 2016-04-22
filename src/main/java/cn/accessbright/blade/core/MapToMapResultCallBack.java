package cn.accessbright.blade.core;

import cn.accessbright.blade.core.utils.Strings;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 将查询结果处理为 Map--> key(主键):String; value(数据行): Map->key(列名);value(单元格值) <br>
 *
 * @author ll
 * @see 注意：查询语句中的key，必须在第一位
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
        if (Strings.equalsIgnoreCase(keyColumnName, columnName)) {
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
            theValue.put(getCanonicalName(columnName), KqPeriodTime.Tools.toString(value));
        }
    }

    public Map getData() {
        return data;
    }
}