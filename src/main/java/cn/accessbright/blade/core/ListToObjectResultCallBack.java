package cn.accessbright.blade.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将查询结果处理为
 * List-->
 * value(数据行):
 * Object->property(列名);value(单元格值)
 * <br>
 *
 * @author ll
 */
public class ListToObjectResultCallBack extends QueryResultCallBack {
    private List data = new ArrayList();
    private Object rowData;
    private Class clazz;
    private Map propertyMapper;


    public ListToObjectResultCallBack(Class clazz) {
        this.clazz = clazz;
        rowData = newTarget();
        propertyMapper = Tools.getPropNameMapper(clazz, true);
    }

    public void read(int columIndex, String columnName, Object value) {
        String colName = getCanonicalName(columnName);

        Tools.setPropValue(rowData, (String) propertyMapper.get(colName), Tools.toString(value));
        if (columIndex == columnCount - 1) {// 到末尾列，则将数据对象添加到List列表中
            data.add(rowData);
            rowData = newTarget();
        }
    }

    private Object newTarget() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public List getData() {
        return data;
    }
}
