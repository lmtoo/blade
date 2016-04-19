package cn.accessbright.blade.core;

import cn.accessbright.blade.core.excel.ExcelHeader;
import cn.accessbright.blade.core.excel.ExcelRow;
import cn.accessbright.blade.core.region.CompositeRegion;
import cn.accessbright.blade.core.region.LeafRegion;
import cn.accessbright.blade.core.region.Point;
import cn.accessbright.blade.core.region.RegionVisitor;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * excel导出模版
 *
 * @author Neusoft
 */
public abstract class ExcelExportTemplate extends FileExporter {
    private List data;
    private Object headerNames;
    private String sheetName;
    private int width;

    public ExcelExportTemplate(String path, String sheetName, int width, List data) {
        this(path, sheetName, Collections.EMPTY_LIST, width, data);
    }

    public ExcelExportTemplate(String path, String sheetName, String[] headerNames, List data) {
        this(path, sheetName, (Object) headerNames, headerNames.length, data);
    }

    public ExcelExportTemplate(String path, String sheetName, ExcelHeader header, List data) {
        this(path, sheetName, header, header.width(), data);
    }

    /**
     * 构造函数
     *
     * @param path
     * @param sheetName
     * @param headerNames 列头的名称，默认为String数组
     * @param data
     */
    public ExcelExportTemplate(String path, String sheetName, Object headerNames, int width, List data) {
        super(path);
        this.sheetName = sheetName;
        this.headerNames = headerNames;
        this.data = data;
        this.width = width;
    }

    protected void handleGenerateFile(String filepath) throws FileNotFoundException, IOException {
        POITools tools = POITools.createSXSSFWorkbook(500);
        Sheet sheet = tools.createSheet(sheetName);

        // 添加总标题
        int rowIndex = 0;

        rowIndex += createTitle(tools, sheet, rowIndex);
        rowIndex += createHeader(tools, sheet, rowIndex);

        // 添加数据
        if (!Tools.isEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                ExcelRow row = tools.createRow(sheet, rowIndex++, 0);
                map(data.get(i), row, i + 1);
            }
        }

        rowIndex += createSummary(tools, sheet, rowIndex);
        rowIndex += createFooter(tools, sheet, rowIndex);
        tools.dispose(new FileOutputStream(filepath));
    }

    /**
     * 创建excel的标题
     *
     * @param tools
     * @param sheet
     * @param currentRowIndex
     * @return 标题所占用的行数
     */
    protected int createTitle(POITools tools, Sheet sheet, int currentRowIndex) {
        tools.createRow(sheet, currentRowIndex, 0).mergeString(getColumnLength(), sheet.getSheetName());
        return 1;
    }

    /**
     * 创建excel的列头
     *
     * @param tools
     * @param sheet
     * @param currentRowIndex 当前excel的行数
     * @return 列头所占用的行数
     */
    protected int createHeader(final POITools tools, final Sheet sheet, int currentRowIndex) {
        if (headerNames instanceof ExcelHeader) {
            ExcelHeader header = (ExcelHeader) headerNames;
            header.resetRowIndex(currentRowIndex);
            header.accept(new RegionVisitor() {
                public void visit(LeafRegion leaf) {
                    Point leftTop = leaf.absolute();
                    if (leaf.isMinimize()) {
                        tools.addString(sheet, leftTop.left(), leftTop.top(), leaf.content(), ExcelRow.TEXT_ALIGN_CENTER);
                    } else {
                        tools.margeRectangle(sheet, leftTop.top(), leftTop.left(), leaf.width(), leaf.height(), Tools.toString(leaf.content()));
                    }
                }

                public void visit(CompositeRegion composit) {
                }
            });
            return header.height();
        } else {
            tools.createRow(sheet, currentRowIndex, 0).addString((String[]) headerNames, ExcelRow.TEXT_ALIGN_CENTER);
            return 1;
        }
    }

    /**
     * 创建excel的尾部
     *
     * @param tools
     * @param sheet
     * @param currentRowIndex
     * @return 尾部所占用的行数
     */
    protected int createSummary(POITools tools, Sheet sheet, int currentRowIndex) {
        return 0;
    }

    /**
     * 创建excel的尾部
     *
     * @param tools
     * @param sheet
     * @param currentRowIndex
     * @return 尾部所占用的行数
     */
    protected int createFooter(POITools tools, Sheet sheet, int currentRowIndex) {
        return 0;
    }

    /**
     * 获取该excel所占用的列数
     *
     * @return
     */
    protected int getColumnLength() {
        return width;
    }

    protected abstract void map(Object data, ExcelRow row, int dataIndex);
}