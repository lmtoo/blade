package cn.accessbright.blade.core.excel;

import cn.accessbright.blade.core.POITools;
import cn.accessbright.blade.core.Tools;
import cn.accessbright.blade.core.TreeNode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;


public class TreeExcelParser {

    private TreeNode root = TreeNode.valueOf("root");

    public static final int DEFAULT_TREE_LEVEL = 2;

    private int treeLevel = DEFAULT_TREE_LEVEL;

    public TreeNode getRoot() {
        return root;
    }

    public void parse(String path) throws Exception {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            throw new IllegalArgumentException("ָ指定文件不存在");
        }
        parse(new FileInputStream(f));
    }

    public void parse(InputStream in) throws Exception {
        POITools tools = POITools.load(in);
        Iterator iter = tools.sheetIterator();

        while (iter.hasNext()) {
            Sheet sheet = (Sheet) iter.next();
            parseSheet(root, tools, sheet);
        }
    }

    private void parseSheet(TreeNode context, POITools tools, Sheet sheet) {
        String name = sheet.getSheetName();
        TreeNode sheetNode = context.addChild(name);

        int mergedCount = getRegionCount(tools, sheet);
        for (int i = 0; i < mergedCount; i++) {
            int startColIndex = i * treeLevel;
            int rowCount = getRegionRowCount(tools, sheet, startColIndex + 1);
            String regionName = tools.getValue(sheet, startColIndex, 0);
            TreeNode regionNode = sheetNode.addChild(regionName);
            CellRangeAddress region = new CellRangeAddress(0, rowCount - 1, startColIndex + 1, startColIndex + 1);
            parseRegion(tools, sheet, region, regionNode);
        }
    }

    /**
     * 获取区域的行数
     *
     * @return
     */
    private int getRegionRowCount(POITools tools, Sheet sheet, int colIndex) {
        int row = 0;
        while (Tools.isNotEmpty(tools.getValue(sheet, colIndex, row))) {
            row++;
        }
        return row;
    }

    /**
     * 获取区域的个数
     *
     * @param tools
     * @param sheet
     * @return
     */
    private int getRegionCount(POITools tools, Sheet sheet) {
        int currentColumnIndex = 0;
        int level = 0;
        while (Tools.isNotEmpty(tools.getValue(sheet, currentColumnIndex, 0))) {
            currentColumnIndex += treeLevel;
            level++;
        }
        return level;
    }

    private void parseRegion(POITools tools, Sheet sheet, CellRangeAddress region, final TreeNode context) {
        tools.forEachCell(sheet, region, new POITools.CellVisitor() {
            public void visit(int rowIndex, int cellIndex, Cell cell, String value) {
                context.addChild(value);
            }
        });
    }
}