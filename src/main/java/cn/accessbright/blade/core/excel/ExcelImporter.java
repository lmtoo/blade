package cn.accessbright.blade.core.excel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Excel导入工具
 *
 * @author ll
 */
public interface ExcelImporter {
    /**
     * 1、跳转到导入页面
     */
    Object toImport(HttpServletRequest request, Map params);

    /**
     * 2、上传Excel
     */
    Object doUpload(HttpServletRequest request);

    /**
     * 3、执行导入数据
     */
    Object doImport(HttpServletRequest request);
}