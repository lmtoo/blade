package cn.accessbright.blade.core.utils;

import cn.accessbright.blade.core.utils.collections.ObjectFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lile_ on 2016/4/24.
 */
public class RowUtils {
    /**
     * 查找第一个匹配条件的数据，如果未找到返回null
     *
     * @param rows
     * @param filter
     * @param valueIndex
     * @return
     */
    public static String[] firstMatch(Iterable<String[]> rows, ObjectFilter<String[]> filter, int[] valueIndex) {
        Iterator<String[]> iter = rows.iterator();
        while (iter.hasNext()) {
            String[] row = iter.next();
            if (filter.isMatch(row)) {
                return RowDataMapper.DEFAULT_ROWDATA_MAPPER.map(row, valueIndex);
            }
        }
        return null;
    }

    /**
     * 过滤数据，对行和列两个纬度进行数据过滤
     *
     * @param rows
     * @param filter
     * @param valueIndex
     * @return
     */
    public static List<String[]> filterRow(Iterable<String[]> rows, ObjectFilter<String[]> filter, int[] valueIndex) {
        List<String[]> result = new ArrayList<>();
        for (String[] row : rows) {
            if (filter.isMatch(row)) {
                result.add(RowDataMapper.DEFAULT_ROWDATA_MAPPER.map(row, valueIndex));
            }
        }
        return result;
    }


    /**
     * 将行映射为map
     *
     * @param rows       行数据
     * @param keyIndex   键所在的数组索引
     * @param valueIndex map中value所在的索引， 如果valueIndex长度大于1，按照索引收集数组中的数据生成一个新数组作为value值，
     *                   如果valueIndex只有一个索引，则直接将数组中的该索引数据作为value，
     *                   如果valueIndex为null或者长度为1，则将整行数据作为value值
     * @return
     */
    public static Map<String, String[]> rowsToMap(Iterable<String[]> rows, int keyIndex, int[] valueIndex) {
        Map<String, String[]> dict = new HashMap<>();
        for (String[] row : rows) {
            String key = row[keyIndex];
            String[] value = RowDataMapper.DEFAULT_ROWDATA_MAPPER.map(row, valueIndex);
            dict.put(key, value);
        }
        return dict;
    }
}
