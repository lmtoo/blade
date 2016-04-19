package cn.accessbright.blade.core.utils;

/**
 * 行数据过滤器,对一行中的列数据进行过滤，作用与ObjectMapper类似，
 * 不过ObjectMapper主要映射对象，而ColumnDataFilter主要映射数组
 *
 * @author ll
 */
public interface RowDataMapper<T> {
    /**
     * @param row        要过滤的行数据
     * @param valueIndex 要返回的列数据索引
     * @return
     */
    T map(String[] row, int[] valueIndex);


    /**
     * 对一行中的列数据进行过滤，默认策略
     *
     * @param row
     * @param valueIndex
     * map中value所在的索引， 如果valueIndex长度大于1，按照索引收集数组中的数据生成一个新数组作为value值，
     * 如果valueIndex只有一个索引，则直接将数组中的该索引数据作为value，
     * 如果valueIndex为null或者长度为1，则将整行数据作为value值
     */
    RowDataMapper<String[]> DEFAULT_ROWDATA_MAPPER = new RowDataMapper<String[]>() {
        public String[] map(String[] row, int[] valueIndex) {
            if (valueIndex == null) return new String[]{};
            String[] value = row;
            if (valueIndex.length == 1) {
                value = new String[]{row[valueIndex[0]]};
            } else {
                int colIndex = 0;
                value = new String[valueIndex.length];
                for (int i = 0; i < valueIndex.length; i++) {
                    value[colIndex++] = row[valueIndex[i]];
                }
            }
            return value;
        }
    };
}