package cn.accessbright.blade.core.excel;

import cn.accessbright.blade.core.utils.RowValidateRules;
import cn.accessbright.blade.core.utils.collections.Pair;

import java.util.List;
import java.util.Map;


/**
 * Excel导入数据支持类<br>
 * FileGenerator的generateFile负责：
 * 获取相对应用跟路径位置的导入模版<br>
 * 如果返回值以/开头则表示上下文绝对路径，否则表示生成的文件名
 *
 * @author ll
 */
public interface ExcelImportDataSupport {
    /**
     * 获取导入页面的title和备注
     *
     * @return
     */
    Pair getPageTitleAndNote(Map params);

    /**
     * 获取验证规则
     *
     * @param params
     * @return
     */
    RowValidateRules getExcelValidateRules(Map params);

    /**
     * 最终的导入数据功能
     *
     * @param importedData 符合manager批量导入的数据
     * @param params       请求导入页面的参数
     */
    void doBatchImport(Map params, List importedData);


    /**
     * 生成模版文件<br>
     * 如果返回值以/开头则表示上下文绝对路径，否则表示基于baseDir目录生成的文件名
     *
     * @param params
     * @param baseDir
     * @return
     */
    String doGenerateTemplate(Map params, String baseDir);
}