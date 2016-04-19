package cn.accessbright.blade.core.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Excel行操作API
 *
 * @author ll
 */
public interface ExcelRow {
    //文本所在单元格的位置
    int TEXT_ALIGN_ORIGNAL = -1;
    //靠左对齐
    int TEXT_ALIGN_LEFT = 0;
    //靠右对齐
    int TEXT_ALIGN_RIGHT = 1;
    //居中对齐
    int TEXT_ALIGN_CENTER = 2;

    // =================================设置字符串值=================================
    ExcelRow addString(String value);

    ExcelRow addString(Object value);

    ExcelRow addString(int value);

    ExcelRow addString(String prop, Object target);

    ExcelRow addString(String[] props, Object target);

    ExcelRow addString(String[] values);

    ExcelRow addString(List values);

    ExcelRow addString(Object[] values);

    ExcelRow addString(Object[] values, int start);

    ExcelRow addString(Object[] values, int start, int end);


    ExcelRow addString(String key, Map values);

    ExcelRow addString(List keys, Map values);

    ExcelRow addString(String[] keys, Map values);

    ExcelRow addString(String key, Map values, boolean ignoreKeyCase);

    ExcelRow addString(List keys, Map values, boolean ignoreKeyCase);

    // =================================设置字符串值=================================
    ExcelRow addString(String value, int align);

    ExcelRow addString(String[] values, int align);

    ExcelRow addString(List values, int align);

    ExcelRow addString(String key, Map values, int align);

    ExcelRow addString(List keys, Map values, int align);

    ExcelRow addString(String key, Map values, boolean ignoreKeyCase, int align);

    ExcelRow addString(List keys, Map values, boolean ignoreKeyCase, int align);

    // =================================设置数字值=================================
    ExcelRow addNumber(double value);// 默认保留两位小数

    ExcelRow addNumber(String value);// 默认保留两位小数

    ExcelRow addNumber(Object value);// 默认保留两位小数

    ExcelRow addNumber(Object[] values);

    ExcelRow addNumber(Object[] values, int begin);

    ExcelRow addNumber(Object[] values, int begin, int length);


    ExcelRow addNumber(String key, Map values);

    ExcelRow addNumber(List keys, Map values);

    ExcelRow addNumber(String[] keys, Map values);

    ExcelRow addNumber(Iterator keysIter, Map values);

    ExcelRow addNumber(String key, Map values, boolean ignoreKeyCase);

    ExcelRow addNumber(List keys, Map values, boolean ignoreKeyCase);

    ExcelRow addNumber(Iterator keysIter, Map values, boolean ignoreKeyCase);

    // 指定保留小数位数
    ExcelRow addNumber(double value, int digits);

    ExcelRow addNumber(String value, int digits);

    ExcelRow addNumber(String key, Map values, int digits);

    ExcelRow addNumber(List keys, Map values, int digits);

    ExcelRow addNumber(String key, Map values, boolean ignoreKeyCase, int digits);

    ExcelRow addNumber(List keys, Map values, boolean ignoreKeyCase, int digits);

    // =================================设置数字值=================================

    ExcelRow addInteger(int value);

    ExcelRow addInteger(Integer value);

    ExcelRow addInteger(String value);

    ExcelRow addInteger(String key, Map values);

    ExcelRow addInteger(List keys, Map values);

    ExcelRow addInteger(String key, Map values, boolean ignoreKeyCase);

    ExcelRow addInteger(List keys, Map values, boolean ignoreKeyCase);

    // =================================合并单元格并设置值=================================

    /**
     * 合并当前行中指定索引之间的单元格，并设置值
     *
     * @param from  起始单元格索引
     * @param to    结束单元格索引
     * @param value 被设置的值
     * @return
     */
    ExcelRow mergeString(int from, int to, String value);

    /**
     * 合并当前行中当前单元格之后指定长度的单元格，并设置值
     *
     * @param length 要合并的单元格的长度
     * @param value  被设置的值
     * @return
     */
    ExcelRow mergeString(int length, String value);

    /**
     * 合并当前行中指定索引之间的单元格，并设置值
     *
     * @param from  起始单元格索引
     * @param to    结束单元格索引
     * @param value 被设置的值
     * @return
     */
    ExcelRow mergeString(int from, int to, String value, int align);

    /**
     * 合并当前行中当前单元格之后指定长度的单元格，并设置值
     *
     * @param length 要合并的单元格的长度
     * @param value  被设置的值
     * @return
     */
    ExcelRow mergeString(int length, String value, int align);
}