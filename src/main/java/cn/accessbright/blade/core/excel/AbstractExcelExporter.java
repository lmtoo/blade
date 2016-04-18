package cn.accessbright.blade.core.excel;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * @author ll
 */
public abstract class AbstractExcelExporter implements ExcelExporter {
    public Object doExport(HttpServletRequest request, Map params) {
        String baseDir = request.getSession().getServletContext().getRealPath("/") + File.separator + "file" + File.separator + "wage" + File.separator + "download";
        String url = request.getContextPath();
        String fileNameOrPath = doGenerateExcel(params, baseDir);
        if (fileNameOrPath != null && fileNameOrPath.startsWith("/")) {
            url += fileNameOrPath;
        } else {
            url += "/file/wage/download/" + fileNameOrPath;
        }
        request.setAttribute("url", url);
        return "download";
    }

    protected abstract String doGenerateExcel(Map params, String baseDir);
}