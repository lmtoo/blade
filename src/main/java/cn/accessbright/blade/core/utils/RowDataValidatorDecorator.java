package cn.accessbright.blade.core.utils;

import java.util.List;

/**
 * 行数据验证器装饰器
 *
 * @author ll
 */
public class RowDataValidatorDecorator implements RowDataValidator {
    private RowDataValidator validator;

    public RowDataValidatorDecorator(RowDataValidator validator) {
        this.validator = validator;
    }

    public boolean isValidate(String[] rowItem, List<String> errorInfo, int rowIndex) {
        if (rowIndex > 1) {
            if (validator != null && !validator.isValidate(rowItem, errorInfo, rowIndex))
                return false;
            int beforeErrorCount = errorInfo.size();// 验证其他字段之前的错误消息个数
            validateNonFirstRow(rowItem, errorInfo, rowIndex);
            int afterErrorCount = errorInfo.size();// 验证其他字段之后的错误消息个数
            return beforeErrorCount == afterErrorCount;
        }
        return true;
    }

    /**
     * 验证其余的非人员信息列
     *
     * @param rowItem
     * @param errorInfo
     * @param rowIndex
     * @return
     */
    protected void validateNonFirstRow(String[] rowItem, List errorInfo, int rowIndex) {
    }
}