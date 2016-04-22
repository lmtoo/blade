package cn.accessbright.blade.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 将查询结果处理为 List--> value(数据行): Map->key(列名);value(单元格值) <br>
 *
 * @author ll
 */
public class ListToMapResultCallBack extends QueryResultCallBack {
    private List data = new ArrayList();
    private transient Map rowData = new LinkedHashMap();

    public void read(int columIndex, String columnName, Object value) {
        String canonicalName = getCanonicalName(columnName);
        rowData.put(canonicalName, KqPeriodTime.Tools.toString(value));
        if (columIndex == columnCount - 1) {//  到末尾列，则将列数据添加到List列表中
            data.add(rowData);
            rowData = new LinkedHashMap();
        }
    }

    public List getData() {
        return data;
    }
}
