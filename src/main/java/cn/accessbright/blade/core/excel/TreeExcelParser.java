package cn.accessbright.blade.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.core.util.TreeNode;
import com.icitic.hrms.sys.action.AdminDivislonAction.DsyDataGenerator;
import com.icitic.hrms.util.POITools;
import com.icitic.hrms.util.POITools.CellVisitor;
import com.icitic.hrms.util.Tools;

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
			throw new HrmsException("ָ���ļ�������", TreeExcelParser.class);
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
			CellRangeAddress region = new CellRangeAddress(0, rowCount-1, startColIndex+1, startColIndex+1);
			parseRegion(tools, sheet, region, regionNode);
		}
	}

	/**
	 * ��ȡ���������
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
	 * ��ȡ����ĸ���
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
		tools.forEachCell(sheet, region, new CellVisitor() {
			public void visit(int rowIndex, int cellIndex, Cell cell, String value) {
				context.addChild(value);
			}
		});
	}

	public static void main(String[] args) throws Exception {
		TreeExcelParser parser = new TreeExcelParser();
		parser.parse("f:/adminDivislon.xls");
		parser.getRoot();

		DsyDataGenerator dataGenerator = new DsyDataGenerator();
		parser.getRoot().accept(dataGenerator);

		Iterator iter = dataGenerator.getData().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			System.out.println(entry.getKey());
			System.out.println(ListArrayUtil.join((List) entry.getValue(), ","));
		}
	}
}