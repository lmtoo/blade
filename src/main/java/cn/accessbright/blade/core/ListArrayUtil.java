package cn.accessbright.blade.core;

import jxl.Workbook;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 数组列表工具类
 *
 * @author ll
 */
public class ListArrayUtil {

    /**
     * 将excel表格中的数据导入List中
     *
     * @param path                excel所在路径
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           每一数据行的验证器
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArray(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator,
                                             String messageSeparator) throws HrmsException {
        List fixedNames = fixedColumnNames != null ? Arrays.asList(fixedColumnNames) : new ArrayList();

        List errorInfo = new ArrayList();
        FileInputStream fis = null;
        try {
            File f = new File(path);
            if (!f.exists() || !f.isFile()) {
                throw new HrmsException("指定文件不存在", ListArrayUtil.class);
            }
            fis = new FileInputStream(f);

            POITools tools = POITools.load(fis);


            // 获取第一张Sheet表
            Sheet sheet = tools.getFirstSheet();
            int rows = tools.getRowCount(sheet); // sheet 行数

            int columns = tools.getColunmCount(sheet, 0); // sheet 列数
            if (rows == 0 || rows < 1 || columns == 0 || columns < 1) {
                errorInfo.add("导入的文件内容为空");
            }

            int needCols = columns;
            // 表头正式开始

            ExcelSheetData sheetData = new ExcelSheetData(errorInfo, requiredColunmIndex, validator);

            // 正式开始
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {// 行
                String[] rowItem = new String[needCols];
                for (int columnIndex = 0; columnIndex < needCols; columnIndex++) { // 列
                    String value = Strings.trim(tools.getValue(sheet, columnIndex, rowIndex));
                    rowItem[columnIndex] = Tools.isEmpty(value) ? null : value;
                }

                if (rowIndex == 0) {
                    if (!fixedNames.isEmpty() && !Arrays.asList(rowItem).equals(fixedNames)) {
                        errorInfo.add("文件格式不正确，表头列名不正确");
                        throw new HrmsException("文件格式不正确", ListArrayUtil.class);
                    }
                }
                sheetData.addRowData(rowItem);
            }
            sheetData.checkValidate();
            return sheetData.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
                }
            }
            if (!errorInfo.isEmpty()) {
                throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
            }
        }
    }

    /**
     * 将excel表格中的数据导入List中
     *
     * @param path                excel所在路径
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           每一数据行的验证器
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArrayByJxl(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator,
                                                  String messageSeparator) throws HrmsException {
        List fixedNames = fixedColumnNames != null ? Arrays.asList(fixedColumnNames) : new ArrayList();

        List errorInfo = new ArrayList();
        FileInputStream fis = null;
        try {
            File f = new File(path);
            if (!f.exists() || !f.isFile()) {
                throw new HrmsException("指定文件不存在", ListArrayUtil.class);
            }
            fis = new FileInputStream(f);

            JxlTools tools = JxlTools.getInstance();

            Workbook wbook = null;
            wbook = Workbook.getWorkbook(fis);
            //获取第一张Sheet表
            jxl.Sheet sheet = wbook.getSheet(0);
            int rows = sheet.getRows();  // sheet 行数
            int columns = sheet.getColumns(); // sheet 列数

            if (rows == 0 || rows < 1 || columns == 0 || columns < 1) {
                errorInfo.add("导入的文件内容为空");
            }

            int needCols = columns;
            // 表头正式开始

            ExcelSheetData sheetData = new ExcelSheetData(errorInfo, requiredColunmIndex, validator);

            // 正式开始
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {// 行
                String[] rowItem = new String[needCols];
                for (int columnIndex = 0; columnIndex < needCols; columnIndex++) { // 列
                    String value = tools.getValue(sheet, columnIndex, rowIndex);
                    rowItem[columnIndex] = Tools.isEmpty(value) ? null : value;
                }

                if (rowIndex == 0) {
                    if (!fixedNames.isEmpty() && !Arrays.asList(rowItem).equals(fixedNames)) {
                        errorInfo.add("文件格式不正确，表头列名不正确");
                        throw new HrmsException("文件格式不正确", ListArrayUtil.class);
                    }
                }
                sheetData.addRowData(rowItem);
            }
            sheetData.checkValidate();
            return sheetData.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
                }
            }
            if (!errorInfo.isEmpty()) {
                throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
            }
        }
    }

    private static class ExcelSheetData {
        private int[] requiredColunmIndex;
        private List errorInfo;
        private Stack data = new Stack();
        private RowDataValidator validator;

        public ExcelSheetData(List errorInfo, int[] requiredColunmIndex, RowDataValidator validator) {
            this.errorInfo = errorInfo;
            this.validator = validator;
            this.requiredColunmIndex = requiredColunmIndex;
        }

        public void addRowData(String[] rowItem) {
            data.push(rowItem);
        }

        public void deleteEmptyRows() {
            String[] last = (String[]) data.lastElement();
            while (isAllElementEmtpy(last)) {
                data.remove(last);
                last = (String[]) data.lastElement();
            }
        }

        private boolean isAllElementEmtpy(String[] row) {
            for (int i = 0; i < row.length; i++) {
                if (!Tools.isEmpty(row[i])) return false;
            }
            return true;
        }

        /**
         * 检查中间的空值
         */
        public void checkValidate() {
            deleteEmptyRows();
            for (int i = 0; i < data.size(); i++) {
                int rowIndex = i + 1;
                String[] row = (String[]) data.get(i);
                if (isAllElementEmtpy(row)) {
                    errorInfo.add("第" + rowIndex + "行整行为空值");
                } else {
                    for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                        String value = row[columnIndex];
                        if (ArrayUtils.contains(requiredColunmIndex, columnIndex) && Tools.isEmpty(value)) {
                            errorInfo.add("第" + rowIndex + "行第" + (columnIndex + 1) + "列:为空值");
                        }
                    }
                    validator.isValidate(row, errorInfo, rowIndex);
                }
            }
        }

        public List getData() {
            return data;
        }
    }


    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割，默认不进行列名、是否为空验证，并且不验证行数据的正确性
     *
     * @param path
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArray(String path) throws HrmsException {
        return importDataToListArray(path, null, null);
    }

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割，默认不验证行数据的正确性
     *
     * @param path                excel地址
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArray(String path, String[] fixedColumnNames, Integer[] requiredColunmIndex) throws HrmsException {
        return importDataToListArray(path, fixedColumnNames, requiredColunmIndex, new RowDataValidator() {
            public boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
                return true;
            }
        });
    }

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割
     *
     * @param path                excel地址
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           验证行数据的正确性
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArray(String path, String[] fixedColumnNames, Integer[] requiredColunmIndex, RowDataValidator validator)
            throws HrmsException {
        return importDataToListArray(path, fixedColumnNames, ArrayUtils.toPrimitive(requiredColunmIndex), validator, "<br>");
    }

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割
     *
     * @param path                excel地址
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           验证行数据的正确性
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArray(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator)
            throws HrmsException {
        return importDataToListArray(path, fixedColumnNames, requiredColunmIndex, validator, "<br>");
    }

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割
     *
     * @param path                excel地址
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           验证行数据的正确性
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArray(String path, ExcelValidateRules rules) throws HrmsException {
        return importDataToListArray(path, rules.getFixedColumnNames(), rules.getRequiredColunmIndex(), rules, rules.getMessageSeparator());
    }

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割
     *
     * @param path                excel地址
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           验证行数据的正确性
     * @return
     * @throws HrmsException
     */
    public static List importDataToListArrayByJxl(String path, ExcelValidateRules rules) throws HrmsException {
        return importDataToListArrayByJxl(path, rules.getFixedColumnNames(), rules.getRequiredColunmIndex(), rules, rules.getMessageSeparator());
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
    public static Map rowsToMap(List rows, int keyIndex, int[] valueIndex) {
        Map dict = new HashMap();
        Iterator iter = rows.iterator();
        iter.next();// 跳过表头
        while (iter.hasNext()) {
            String[] row = (String[]) iter.next();
            String key = row[keyIndex];
            Object value = DEFAULT_ROWDATA_MAPPER.map(row, valueIndex);
            dict.put(key, value);
        }
        return dict;
    }

    /**
     * 过滤数据，对行和列两个纬度进行数据过滤
     *
     * @param rows
     * @param filter
     * @param valueIndex
     * @return
     */
    public static List filterRow(List rows, ObjectReducer reducer, int[] valueIndex) {
        List result = new ArrayList();
        Iterator iter = rows.iterator();
        iter.next();// 跳过表头
        while (iter.hasNext()) {
            String[] row = (String[]) iter.next();
            if (reducer.reduce(row)) {
                result.add(DEFAULT_ROWDATA_MAPPER.map(row, valueIndex));
            }
        }
        return result;
    }

    /**
     * 查找第一个匹配条件的数据，如果未找到返回null
     *
     * @param rows
     * @param filter
     * @param valueIndex
     * @return
     */
    public static Object firstMatch(List rows, ObjectReducer reducer, int[] valueIndex) {
        Iterator iter = rows.iterator();
        iter.next();// 跳过表头
        while (iter.hasNext()) {
            String[] row = (String[]) iter.next();
            if (reducer.reduce(row)) {
                return DEFAULT_ROWDATA_MAPPER.map(row, valueIndex);
            }
        }
        return null;
    }



    /**
     * 计算data中的合计，data中必须保存为Map，且Map的value值必须为数字
     *
     * @param data
     * @return
     */
    public static Map totalOfItems(Collection data, Collection summaryColumns) {
        return totalOfItems(data.iterator(), summaryColumns);
    }

    public static String totalOfItems(Collection data, String summaryColumn) {
        return totalOfItems(data.iterator(), summaryColumn);
    }

    public static String totalOfItems(Iterator dataIter, String summaryColumn) {
        String total = Tools.ZERO.toString();
        while (dataIter.hasNext()) {
            Object item = dataIter.next();
            String subValue = Tools.getPropText(item, summaryColumn);
            total = MathExtend.add(total, Tools.filterNullToZero(subValue));
        }
        return total;
    }

    public static Map totalOfItems(Iterator dataIter, Collection numericKeys) {
        return totalOfItems(dataIter, new Collection[]{numericKeys});
    }

    public static Map totalOfItems(Iterator dataIter, Collection[] numericKeys) {
        Map total = new HashMap();
        while (dataIter.hasNext()) {
            Object item = dataIter.next();
            for (int i = 0; i < numericKeys.length; i++) {
                Iterator keyIter = numericKeys[i].iterator();
                while (keyIter.hasNext()) {
                    String key = (String) keyIter.next();
                    accumulateValueTo(total, key.toLowerCase(), item);
                }
            }
        }
        return total;
    }

    public static Map totalOfItems(Iterator dataIter, String[] numericKeys) {
        return totalOfItems(dataIter, new String[][]{numericKeys});
    }

    public static Map totalOfItems(Iterator dataIter, String[][] numericKeys) {
        Map total = new HashMap();
        while (dataIter.hasNext()) {
            Object item = dataIter.next();
            for (int i = 0; i < numericKeys.length; i++) {
                for (int j = 0; j < numericKeys[i].length; j++) {
                    String key = numericKeys[i][j];
                    accumulateValueTo(total, key, item);
                }
            }
        }
        return total;
    }

    private static void accumulateValueTo(Map total, String key, Object source) {
        String totalValue = total.get(key) + "";
        String subValue = Tools.getPropText(source, key);
        String value = MathExtend.add(Tools.filterNullToZero(totalValue), Tools.filterNullToZero(subValue));
        total.put(key, value);
    }


    /**
     * 将集合对象映射到map
     *
     * @param source 要映射的集合
     * @param key    集合对象中作为map的key的属性名称
     * @param value  集合对象中作为map的value的属性名称，{@linkplain com.icitic.hrms.util.Tools.REF_SELF}为引用集合对象自身
     * @return
     */
    public static Map toMap(Collection source, String key, String value) {
        Map result = new HashMap();
        if (Tools.isNotEmpty(source)) {
            Iterator iter = source.iterator();
            while (iter.hasNext()) {
                Object item = (Object) iter.next();
                result.put(Tools.getPropValue(item, key), Tools.REF_SELF.equals(value) ? item : Tools.getPropValue(item, value));
            }
        }
        return result;
    }

    /**
     * 将集合对象映射到map，map中的value为引用集合对象自身
     *
     * @param source 要映射的集合
     * @param prop   集合对象中作为map的key的属性名称
     * @return
     */
    public static Map toSelfRefMap(Collection source, String prop) {
        return toMap(source, prop, Tools.REF_SELF);
    }

}