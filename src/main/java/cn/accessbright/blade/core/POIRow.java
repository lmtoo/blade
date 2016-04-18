package cn.accessbright.blade.core;

import org.apache.poi.ss.usermodel.Sheet;

import com.icitic.hrms.core.excel.AbstractExcelRow;
import com.icitic.hrms.core.excel.ExcelRow;

public class POIRow extends AbstractExcelRow {
	private Sheet sheet;
	private POITools tools;

	public POIRow(Sheet sheet, POITools tools, int rowIndex, int startColumnIndex) {
		super(rowIndex, startColumnIndex);
		this.sheet = sheet;
		this.tools = tools;
	}

	protected ExcelRow addNumber(int column, int row, double value, int digits) {
		tools.addNumber(sheet, column, row, value, digits);
		return this;
	}

	protected ExcelRow mergeString(int from, int to, int row, String value, int align) {
		tools.margeString(sheet, from, row, to, row, value, align);
		return this;
	}

	protected ExcelRow addString(int column, int row, String value, int align) {
		tools.addString(sheet, column, row, value, align);
		return this;
	}
}