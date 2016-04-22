package cn.accessbright.blade.core;

import cn.accessbright.blade.core.utils.Strings;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 将查询结果处理为
 * Map-->
 * key(主键):String;
 * value(数据行):String[]
 * <br>
 *
 * @author ll
 * @see 注意：查询语句中的key，必须在第一位
 */
public class MapToArrayResultCallBack extends QueryResultCallBack {
    private String keyColumnName;
    private Map data = new LinkedHashMap();

    public MapToArrayResultCallBack(String keyColumnName) {
        this.keyColumnName = keyColumnName;
    }

    private transient Object theKeyValue;

    public void read(int columIndex, String columnName, Object value) {
        if (Strings.equalsIgnoreCase(keyColumnName, columnName)) {
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
            theValue[columIndex] = KqPeriodTime.Tools.toString(value);
        }
    }

    public Map getData() {
        return data;
    }
}