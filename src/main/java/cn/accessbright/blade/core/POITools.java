package cn.accessbright.blade.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.accessbright.blade.core.excel.ExcelRow;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class POITools {
	private static POITools instance = new POITools();

	private Map numberStyles = new HashMap();
	private CellStyle[] cellFormats;
	private Workbook wb;

	public Sheet createSheet() {
		return wb.createSheet();
	}

	public Sheet createSheet(String sheetName) {
		return wb.createSheet(sheetName);
	}

	public Sheet getSheet(String sheetName) {
		return wb.getSheet(sheetName);
	}

	public Sheet getFirstSheet() {
		return wb.getSheetAt(0);
	}


	public Iterator sheetIterator(){
		List sheets=new ArrayList();
		int count=wb.getNumberOfSheets();
		for (int i = 0; i < count; i++) {
			sheets.add(wb.getSheetAt(i));
		}
		return sheets.iterator();
	}

	public Sheet cloneSheet(int index,String sheetName){
		Sheet theSheet= wb.cloneSheet(index);
		if(!Tools.isEmpty(sheetName)){
			setSheetName(theSheet, sheetName);
		}
		return theSheet;
	}

	public void deleteSheet(int index){
		wb.removeSheetAt(index);
	}

	public void setSheetName(Sheet sheet,String sheetName){
		int index=wb.getSheetIndex(sheet);
		wb.setSheetName(index, sheetName);
	}

	public void addString(Sheet sheet, int column, int row, String value, int align) {
		Cell cell = getCell(sheet, row, column);
		if(align!= ExcelRow.TEXT_ALIGN_ORIGNAL){
			cell.setCellStyle(cellFormats[align]);
		}
		cell.setCellValue(Tools.filterNullToStr(value));
	}

	public void addString(Sheet sheet, int column, int row, Object value, int align) {
		addString(sheet, column, row, Strings.toString(value), align);
	}

	public void addString(Sheet sheet, int column, int row, String value) {
		addString(sheet, column, row, value, ExcelRow.TEXT_ALIGN_LEFT);
	}

	public void addNumber(Sheet sheet, int column, int row, double value, int digits) {
		Cell cell = getCell(sheet, row, column);
		cell.setCellStyle((CellStyle) numberStyles.get(new Integer(digits)));
		cell.setCellValue(value);
	}

	public void addValue(Sheet sheet, int column, int row, String value){
		Cell cell = getCell(sheet, row, column);
		cell.setCellValue(value);
	}

	public String getValue(Sheet sheet, int column, int row) {
		return getValue(sheet, column, row, 2);
	}

	public String getValue(Sheet sheet, int column, int row,int scale) {
		Cell cell = getCell(sheet, row, column);
		if(Cell.CELL_TYPE_NUMERIC==cell.getCellType())
			return Tools.toNumberString(cell.getNumericCellValue(), scale);
		else if(Cell.CELL_TYPE_BOOLEAN==cell.getCellType())
			return cell.getBooleanCellValue()?"1":"0";
		else if(Cell.CELL_TYPE_BLANK==cell.getCellType())
			return "";
		else if(Cell.CELL_TYPE_STRING==cell.getCellType())
			return cell.getStringCellValue().trim();
		else
			return cell.toString().trim();
	}

	public void forEachCell(Sheet sheet,final ValueHandler handler){
		forEachCell(sheet, new CellVisitor() {
			public void visit(int rowIndex, int cellIndex, Cell cell, String value) {
				String newValue=handler.handle(rowIndex, cellIndex, value);
				if(!Tools.isEmpty(newValue)){
					cell.setCellValue(newValue);
				}
			}
		});
	}

	public void  forEachCell(Sheet sheet,int fromRow,int endRow,int fromCol,int endCol,CellVisitor visitor){
		for (int rowIndex = fromRow; rowIndex <= endRow; rowIndex++) {
			for (int colIndex = fromCol; colIndex <= endCol; colIndex++) {
				Cell cell=getCell(sheet, rowIndex, colIndex);
				String value=getValue(sheet, colIndex, rowIndex);
				visitor.visit(rowIndex, colIndex, cell, value);
			}
		}
	}

	public void  forEachCell(Sheet sheet,CellRangeAddress region,CellVisitor visitor){
		forEachCell(sheet, region.getFirstRow(), region.getLastRow(), region.getFirstColumn(), region.getLastColumn(), visitor);
	}

	public void forEachCell(Sheet sheet,CellVisitor visitor){
		Iterator rowIter=sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = (Row) rowIter.next();
			Iterator cellIterator=row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				int rowIndex=cell.getRowIndex();
				int cellIndex=cell.getColumnIndex();
				String value=getValue(sheet, cellIndex, rowIndex);
				visitor.visit(rowIndex, cellIndex, cell, value);
			}
		}
	}

	public static interface CellVisitor{
		void visit(int rowIndex,int cellIndex,Cell cell,String value);
	}

	public static interface ValueHandler{
		String handle(int rowIndex,int cellIndex,String value);
	}

	/**
	 * @deprecated 不准确的方法
	 * @param sheet
	 * @return
	 */
	public int getColunmCount(Sheet sheet,int row) {
		Row theRow=sheet.getRow(row);
		if(theRow==null)return 0;
		return theRow.getPhysicalNumberOfCells();
	}

	public int getMaxCellNum(Sheet sheet,int row){
		Row theRow=sheet.getRow(row);
		if(theRow==null)return 0;
		return theRow.getLastCellNum();
	}

	/**
	 * @deprecated 不准确的方法
	 * @param sheet
	 * @return
	 */
	public int getRowCount(Sheet sheet) {
		return sheet.getPhysicalNumberOfRows();
	}

	public int getMaxRowNum(Sheet sheet){
		return sheet.getLastRowNum();
	}



	public int getCellType(Sheet sheet, int column, int row){
		return getCell(sheet, row, column).getCellType();
	}

	public void addNumber(Sheet sheet, int column, int row, String value, int digits) {
		addNumber(sheet, column, row, Double.parseDouble(Tools.filterNullToZero(value)), digits);
	}

	public void margeString(Sheet sheet, int startColumn, int startRow, int endColumn, int endRow, String value) {
		margeString(sheet, startColumn, startRow, endColumn, endRow, value, ExcelRow.TEXT_ALIGN_CENTER);
	}

	public void margeRectangle(Sheet sheet, int startRow, int startColumn, int width, int hight, String value) {
		margeString(sheet, startColumn, startRow, startColumn+width-1, startRow+hight-1, value, ExcelRow.TEXT_ALIGN_CENTER);
	}

	public void margeString(Sheet sheet, int startColumn, int startRow, int endColumn, int endRow, String value, int align) {
		Cell cell = getCell(sheet, startRow, startColumn);
		if(align!=ExcelRow.TEXT_ALIGN_ORIGNAL){
			cell.setCellStyle(cellFormats[align]);
		}
		cell.setCellValue(Tools.filterNullToStr(value));

		CellRangeAddress address = new CellRangeAddress(startRow, endRow, startColumn, endColumn);
		sheet.addMergedRegion(address);

		Workbook wb = sheet.getWorkbook();
		short borderThin = CellStyle.BORDER_THIN;
		RegionUtil.setBorderBottom(borderThin, address, sheet, wb);
		RegionUtil.setBorderTop(borderThin, address, sheet, wb);
		RegionUtil.setBorderLeft(borderThin, address, sheet, wb);
		RegionUtil.setBorderRight(borderThin, address, sheet, wb);
	}

	public ExcelRow createRow(Sheet sheet, int rowIndex, int startColumnIndex) {
		return new POIRow(sheet, this, rowIndex, startColumnIndex);
	}

	public POITools init(Workbook wb) {
		this.wb = wb;
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setColor((short) Font.COLOR_NORMAL);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font.setStrikeout(false);

		CellStyle textAlignLeftFormat = createTextFormat(font);
		textAlignLeftFormat.setWrapText(true);
		textAlignLeftFormat.setAlignment(CellStyle.ALIGN_LEFT);// 水平居左

		CellStyle textAlignRightFormat = createTextFormat(font);
		textAlignRightFormat.setWrapText(true);
		textAlignRightFormat.setAlignment(CellStyle.ALIGN_RIGHT);// 水平居右

		CellStyle textAlignCenterFormat = createTextFormat(font);
		textAlignCenterFormat.setWrapText(true);
		textAlignCenterFormat.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		textAlignCenterFormat.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中

		cellFormats = new CellStyle[3];
		cellFormats[ExcelRow.TEXT_ALIGN_LEFT] = textAlignLeftFormat;
		cellFormats[ExcelRow.TEXT_ALIGN_RIGHT] = textAlignRightFormat;
		cellFormats[ExcelRow.TEXT_ALIGN_CENTER] = textAlignCenterFormat;

		int count = 3;
		for (int i = 0; i <= count; i++) {
			numberStyles.put(new Integer(i), createNumberFormat(i, font));
		}

		return this;
	}

	private CellStyle createTextFormat(Font font) {
		CellStyle cellFotmat = wb.createCellStyle();
		cellFotmat.setBorderTop(CellStyle.BORDER_THIN);
		cellFotmat.setBorderBottom(CellStyle.BORDER_THIN);
		cellFotmat.setBorderLeft(CellStyle.BORDER_THIN);
		cellFotmat.setBorderRight(CellStyle.BORDER_THIN);
		cellFotmat.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		cellFotmat.setFont(font);
		return cellFotmat;
	}

	private CellStyle createNumberFormat(int digits, Font font) {
		String suffix = Strings.repeatChars(digits, '0');
		String format = "#,##0" + (suffix.length() > 0 ? ("." + suffix) : "");
		CellStyle cellFotmat = wb.createCellStyle();
		cellFotmat.setBorderTop(CellStyle.BORDER_THIN);
		cellFotmat.setBorderBottom(CellStyle.BORDER_THIN);
		cellFotmat.setBorderLeft(CellStyle.BORDER_THIN);
		cellFotmat.setBorderRight(CellStyle.BORDER_THIN);
		cellFotmat.setDataFormat(wb.createDataFormat().getFormat(format));
		cellFotmat.setFont(font);
		return cellFotmat;
	}

	public void dispose(OutputStream out) throws IOException {
		try {
			wb.write(out);
			if (wb instanceof SXSSFWorkbook) {
				((SXSSFWorkbook) wb).dispose();
			}
			numberStyles.clear();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	public void dispose(String filepath) throws IOException {
		OutputStream out = new FileOutputStream(filepath);
		try {
			wb.write(out);
			if (wb instanceof SXSSFWorkbook) {
				((SXSSFWorkbook) wb).dispose();
			}
			numberStyles.clear();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	private Row getRow(Sheet sheet, int row) {
		Row currentRow = sheet.getRow(row);
		if (currentRow == null)
			return sheet.createRow(row);
		return currentRow;
	}

	private Cell getCell(Sheet sheet, int row, int column) {
		Row currentRow = getRow(sheet, row);
		Cell currentCell = currentRow.getCell(column);
		if (currentCell == null)
			return currentRow.createCell(column);
		return currentCell;
	}

	/**
	 * 最多支持1万多列数据
	 *
	 * @return
	 */
	public static POITools createXSSFWorkbook() {
		Workbook wb = new XSSFWorkbook();
		return instance.init(wb);
	}

	public static POITools load(InputStream is) throws IOException {
		Workbook wb = new XSSFWorkbook(is);
		return instance.init(wb);
	}

	public static POITools load(String path) throws IOException {
		Workbook wb = new XSSFWorkbook(path);
		return instance.init(wb);
	}

	/**
	 * 最多支持1万多列数据； The example below writes a sheet with a window of 100 rows.
	 * When the row count reaches 101, the row with rownum=0 is flushed to disk
	 * and removed from memory, when rownum reaches 102 then the row with
	 * rownum=1 is flushed, etc.
	 *
	 * @return
	 */
	public static POITools createSXSSFWorkbook(int maxCacheRow) {
		Workbook wb = new SXSSFWorkbook(maxCacheRow);
		return instance.init(wb);
	}

	/**
	 * 最多支持256列数据
	 *
	 * @return
	 */
	public static POITools createHSSFWorkbook() {
		Workbook wb = new HSSFWorkbook();
		return instance.init(wb);
	}
}