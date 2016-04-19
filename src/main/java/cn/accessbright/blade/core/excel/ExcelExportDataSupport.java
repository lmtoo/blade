package cn.accessbright.blade.core.excel;

import java.util.Map;

/**
 * @author ll
 */
public interface ExcelExportDataSupport {

    /**
     * 如果返回值以/开头则表示上下文绝对路径，否则表示基于baseDir目录生成的文件名
     *
     * @param params
     * @param baseDir
     * @return
     */
    String doGenerateExportExcel(Map params, String baseDir);
}
