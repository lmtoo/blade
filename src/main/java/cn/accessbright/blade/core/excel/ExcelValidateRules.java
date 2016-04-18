package cn.accessbright.blade.core.excel;

import java.util.List;

/**
 * Excel导入时的验证规则
 *
 * @author ll
 */
public class ExcelValidateRules implements RowDataValidator {
    private String[] fixedColumnNames;
    private int[] requiredColunmIndex;
    private RowDataValidator validator;
    private String messageSeparator = "<br>";

    /**
     * 只验证excel表头名称和非空列
     *
     * @param fixedColumnNames    excel表头名称
     * @param requiredColunmIndex 非空列索引
     */
    public ExcelValidateRules(String[] fixedColumnNames, int[] requiredColunmIndex) {
        this.fixedColumnNames = fixedColumnNames;
        this.requiredColunmIndex = requiredColunmIndex;
    }

    /**
     * 对excel表头名称和非空列进行验证，同时提供自定义的行验证器对每一个数据行进行验证
     *
     * @param fixedColumnNames    excel表头名称
     * @param requiredColunmIndex 非空列索引
     * @param validator           数据行验证器
     */
    public ExcelValidateRules(String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator) {
        this(fixedColumnNames, requiredColunmIndex);
        this.validator = validator;
    }

    /**
     * 对excel表头名称和非空列进行验证，同时提供自定义的行验证器对每一个数据行进行验证，同时支持对验证错误消息分隔符的自定义
     *
     * @param fixedColumnNames    excel表头名称
     * @param requiredColunmIndex 非空列索引
     * @param validator           数据行验证器
     * @param messageSeparator    错误消息分隔符
     */
    public ExcelValidateRules(String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator, String messageSeparator) {
        this(fixedColumnNames, requiredColunmIndex, validator);
        this.messageSeparator = messageSeparator;
    }

    /**
     * 对Excel列名进行验证，如果不提供此值，则不验证Excel的列名
     *
     * @return
     */
    public String[] getFixedColumnNames() {
        return fixedColumnNames;
    }

    /**
     * 非空列的索引，对列进行非空验证
     *
     * @return
     */
    public int[] getRequiredColunmIndex() {
        return requiredColunmIndex;
    }

    public RowDataValidator getValidator() {
        return this;
    }

    /**
     * 错误消息分隔符
     *
     * @return
     */
    public String getMessageSeparator() {
        return messageSeparator;
    }

    /**
     * 子类可以通过重新，或者提供一个RowDataValidator来委托行验证
     */
    public boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
        if (validator != null)
            return validator.isValidate(rowItem, errorInfo, rowIndex);
        return false;
    }
}
