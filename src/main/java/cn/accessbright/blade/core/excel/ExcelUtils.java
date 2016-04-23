package cn.accessbright.blade.core.excel;

import cn.accessbright.blade.core.utils.RowDataValidator;
import cn.accessbright.blade.core.utils.RowValidateRules;
import cn.accessbright.blade.core.utils.Strings;
import cn.accessbright.blade.core.utils.collections.Arrays;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by lile_ on 2016/4/24.
 */
public class ExcelUtils {

    /**
     * 将excel表格中的数据导入List中
     *
     * @param path                excel所在路径
     * @param fixedColumnNames    要导入的列名
     * @param requiredColunmIndex 值不能为空的列
     * @param validator           每一数据行的验证器
     * @param messageSeparator    错误消息分隔符
     * @return
     */
    public static List<String[]> importDataToListArray(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator,
                                                       String messageSeparator) {
        List<String> fixedNames = fixedColumnNames != null ? java.util.Arrays.asList(fixedColumnNames) : new ArrayList();

        List errorInfo = new ArrayList();
        FileInputStream fis = null;
        try {
            File f = new File(path);
            if (!f.exists() || !f.isFile()) {
                throw new IllegalArgumentException("指定文件不存在");
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

            ExcelSheetData sheetData = new ExcelSheetData(errorInfo, Arrays.toObject(requiredColunmIndex), validator);

            // 正式开始
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {// 行
                String[] rowItem = new String[needCols];
                for (int columnIndex = 0; columnIndex < needCols; columnIndex++) { // 列
                    String value = Strings.trim(tools.getValue(sheet, columnIndex, rowIndex));
                    rowItem[columnIndex] = Strings.isEmpty(value) ? null : value;
                }

                if (rowIndex == 0) {
                    if (!fixedNames.isEmpty() && !java.util.Arrays.asList(rowItem).equals(fixedNames)) {
                        errorInfo.add("文件格式不正确，表头列名不正确");
                        throw new ExcelException("文件格式不正确");
                    }
                }
                sheetData.addRowData(rowItem);
            }
            sheetData.checkValidate();
            return sheetData.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExcelException(Strings.join(errorInfo.iterator(), messageSeparator));
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ExcelException(Strings.join(errorInfo.iterator(), messageSeparator));
                }
            }
            if (!errorInfo.isEmpty()) {
                throw new ExcelException(Strings.join(errorInfo.iterator(), messageSeparator));
            }
        }
    }

    private static class ExcelSheetData {
        private Integer[] requiredColunmIndex;
        private List<String> errorInfo;
        private Stack data = new Stack();
        private RowDataValidator validator;

        public ExcelSheetData(List<String> errorInfo, Integer[] requiredColunmIndex, RowDataValidator validator) {
            this.errorInfo = errorInfo;
            this.validator = validator;
            this.requiredColunmIndex = requiredColunmIndex;
        }

        public void addRowData(String[] rowItem) {
            data.push(rowItem);
        }

        public void deleteEmptyRows() {
            String[] last = (String[]) data.lastElement();
            while (Arrays.isAllEmpty(last)) {
                data.remove(last);
                last = (String[]) data.lastElement();
            }
        }

        /**
         * 检查中间的空值
         */
        public void checkValidate() {
            deleteEmptyRows();
            for (int i = 0; i < data.size(); i++) {
                int rowIndex = i + 1;
                String[] row = (String[]) data.get(i);
                if (Arrays.isAllEmpty(row)) {
                    errorInfo.add("第" + rowIndex + "行整行为空值");
                } else {
                    for (Integer columnIndex = 0; columnIndex < row.length; columnIndex++) {
                        String value = row[columnIndex];
                        if (Arrays.contain(requiredColunmIndex, columnIndex) && Strings.isEmpty(value)) {
                            errorInfo.add("第" + rowIndex + "行第" + (columnIndex + 1) + "列:为空值");
                        }
                    }
                    validator.isValidate(row, errorInfo, rowIndex);
                }
            }
        }

        public List<String[]> getData() {
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

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割，默认不进行列名、是否为空验证，并且不验证行数据的正确性
     *
     * @param path
     * @return
     */
    public static List importDataToListArray(String path) {
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
     */
    public static List<String[]> importDataToListArray(String path, String[] fixedColumnNames, Integer[] requiredColunmIndex) {
        return importDataToListArray(path, fixedColumnNames, requiredColunmIndex, new RowDataValidator() {
            public boolean isValidate(String[] rowItem, List<String> errorInfo, int rowIndex) {
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
     */
    public static List<String[]> importDataToListArray(String path, String[] fixedColumnNames, Integer[] requiredColunmIndex, RowDataValidator validator) {
        return importDataToListArray(path, fixedColumnNames, Arrays.toPrimitive(requiredColunmIndex), validator, "<br>");
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
     */
    public static List<String[]> importDataToListArray(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator) {
        return importDataToListArray(path, fixedColumnNames, requiredColunmIndex, validator, "<br>");
    }

    /**
     * 将excel表格中的数据导入List中，异常消息默认以<br>
     * 分割
     *
     * @param path
     * @param rules
     * @return
     */
    public static List<String[]> importDataToListArray(String path, RowValidateRules rules) {
        return importDataToListArray(path, rules.getFixedColumnNames(), rules.getRequiredColunmIndex(), rules, rules.getMessageSeparator());
    }
}
