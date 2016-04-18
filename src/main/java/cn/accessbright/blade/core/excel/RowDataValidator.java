package cn.accessbright.blade.core.excel;

import java.util.List;


/**
 * excel数据行验证器
 *
 * @author ll
 */
public interface RowDataValidator {
    /**
     * 数据行是否有效
     *
     * @param rowItem   当前行数据
     * @param errorInfo 存放错误信息的列表
     * @param rowIndex  当前行索引
     * @return
     */
    boolean isValidate(String[] rowItem, List errorInfo, int rowIndex);
}