package cn.accessbright.blade.core.excel;

import cn.accessbright.blade.core.utils.Strings;
import cn.accessbright.blade.core.text.TextFormatter;
import cn.accessbright.blade.core.utils.collections.Collections;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;


/**
 * 格式化Excel<br>
 * 如果data为一个Map则只处理第一个sheet的格式化，<br>
 * 如果data为一个List则会为每一个List元素拷贝第一个sheet模版，并格式化，最后删除第一个sheet模版。<br>
 *
 * @author ll
 */
public class ExcelFormatTemplate extends ExcelTemplateExporter {
    private Object data;

    /**
     * 构造函数
     *
     * @param path
     * @param template
     * @param data
     */
    public ExcelFormatTemplate(String path, String template, Object data) {
        super(path, template);
        this.data = data;
    }

    protected void handleExcel(POITools tools) {
        if (data instanceof Map) {
            handleSingleSheet(tools, tools.getFirstSheet(), (Map) data);
        } else if (data instanceof List) {
            List dataList = (List) data;
            if (!Collections.isEmpty(dataList)) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map sheetData = (Map) dataList.get(i);
                    String sheetName = generateSheetName(sheetData, i);
                    Sheet theSheet = tools.cloneSheet(0, sheetName);
                    handleSingleSheet(tools, theSheet, sheetData);
                }
            }
            tools.deleteSheet(0);
        }
    }

    private void handleSingleSheet(POITools tools, Sheet sheet, final Map data) {
        tools.forEachCell(sheet, new POITools.ValueHandler() {
            public String handle(int rowIndex, int cellIndex, String value) {
                if (!Strings.isEmpty(value)) return TextFormatter.format(value, data);
                return value;
            }
        });
    }

    protected String generateSheetName(Map data, int sheetIndex) {
        return "";
    }
}