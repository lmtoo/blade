package cn.accessbright.blade.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.excel.ExcelValidateRules;
import com.icitic.hrms.core.excel.RowDataValidator;
import com.icitic.hrms.util.JxlTools;
import com.icitic.hrms.util.POITools;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.util.MathExtend;

import jxl.Workbook;

/**
 * 数组列表工具类
 *
 * @author ll
 *
 */
public class ListArrayUtil {
	/**
	 * 对象过滤
	 *
	 * @author ll
	 *
	 */
	public static interface ObjectReducer {
		boolean reduce(Object target);
	}

	/**
	 * 对象属性过滤器，对对象中的某几个属性进行过滤，作用与ColumnDataFilter类似
	 * 不过ObjectMapper主要映射对象，而ColumnDataFilter主要映射数组
	 *
	 * @author ll
	 *
	 */
	public static interface ObjectMapper {
		Object map(Object target);
	}

	/**
	 * 访问者模式，遍历聚合对象，并将最终结果返回
	 * @author ll
	 *
	 */
	public static interface ObjectCollector{
		void collect(Object target);
		Object result();
	}


	/**
	 * 映射化简
	 *
	 * @author ll
	 *
	 */
	public static interface MapReduce extends ObjectReducer, ObjectMapper {}

	/**
	 * 行数据过滤器,对一行中的列数据进行过滤，作用与ObjectMapper类似，
	 * 不过ObjectMapper主要映射对象，而ColumnDataFilter主要映射数组
	 *
	 * @author ll
	 *
	 */
	public static interface RowDataMapper {
		/**
		 *
		 * @param row
		 *            要过滤的行数据
		 * @param valueIndex
		 *            要返回的列数据索引
		 * @return
		 */
		Object map(String[] row, int[] valueIndex);
	}

	/**
	 * 对一行中的列数据进行过滤，默认策略
	 *
	 * @param row
	 * @param valueIndex
	 *            map中value所在的索引， 如果valueIndex长度大于1，按照索引收集数组中的数据生成一个新数组作为value值，
	 *            如果valueIndex只有一个索引，则直接将数组中的该索引数据作为value，
	 *            如果valueIndex为null或者长度为1，则将整行数据作为value值
	 */
	public static final RowDataMapper DEFAULT_ROWDATA_MAPPER = new RowDataMapper() {
		public Object map(String[] row, int[] valueIndex) {
			Object value = row;
			if (valueIndex.length == 1) {
				value = row[valueIndex[0]];
			} else {
				int colIndex = 0;
				value = new String[valueIndex.length];
				for (int i = 0; i < valueIndex.length; i++) {
					((String[]) value)[colIndex++] = row[valueIndex[i]];
				}
			}
			return value;
		}
	};

	/**
	 * 将excel表格中的数据导入List中
	 *
	 * @param path
	 *            excel所在路径
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @param validator
	 *            每一数据行的验证器
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArray(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator,
											 String messageSeparator) throws HrmsException {
		List fixedNames = fixedColumnNames != null ? Arrays.asList(fixedColumnNames) : new ArrayList();

		List errorInfo = new ArrayList();
		FileInputStream fis = null;
		try {
			File f = new File(path);
			if (!f.exists() || !f.isFile()) {
				throw new HrmsException("指定文件不存在", ListArrayUtil.class);
			}
			fis = new FileInputStream(f);

			POITools tools=POITools.load(fis);


			// 获取第一张Sheet表
			Sheet sheet =  tools.getFirstSheet();
			int rows=tools.getRowCount(sheet); // sheet 行数

			int columns=tools.getColunmCount(sheet,0); // sheet 列数
			if (rows == 0 || rows < 1||columns == 0 || columns < 1) {
				errorInfo.add("导入的文件内容为空");
			}

			int needCols = columns;
			// 表头正式开始

			ExcelSheetData sheetData=new ExcelSheetData(errorInfo,requiredColunmIndex,validator);

			// 正式开始
			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {// 行
				String[] rowItem = new String[needCols];
				for (int columnIndex = 0; columnIndex < needCols; columnIndex++) { // 列
					String value =Strings.trim(tools.getValue(sheet, columnIndex, rowIndex));
					rowItem[columnIndex] = Tools.isEmpty(value) ? null :value;
				}

				if (rowIndex == 0) {
					if (!fixedNames.isEmpty() && !Arrays.asList(rowItem).equals(fixedNames)) {
						errorInfo.add("文件格式不正确，表头列名不正确");
						throw new HrmsException("文件格式不正确", ListArrayUtil.class);
					}
				}
				sheetData.addRowData(rowItem);
			}
			sheetData.checkValidate();
			return sheetData.getData();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
				}
			}
			if (!errorInfo.isEmpty()) {
				throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
			}
		}
	}

	/**
	 * 将excel表格中的数据导入List中
	 *
	 * @param path
	 *            excel所在路径
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @param validator
	 *            每一数据行的验证器
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArrayByJxl(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator,
												  String messageSeparator) throws HrmsException {
		List fixedNames = fixedColumnNames != null ? Arrays.asList(fixedColumnNames) : new ArrayList();

		List errorInfo = new ArrayList();
		FileInputStream fis = null;
		try {
			File f = new File(path);
			if (!f.exists() || !f.isFile()) {
				throw new HrmsException("指定文件不存在", ListArrayUtil.class);
			}
			fis = new FileInputStream(f);

			JxlTools tools= JxlTools.getInstance();

			Workbook wbook = null;
			wbook = Workbook.getWorkbook(fis);
			//获取第一张Sheet表
			jxl.Sheet sheet = wbook.getSheet(0);
			int rows = sheet.getRows();  // sheet 行数
			int columns = sheet.getColumns(); // sheet 列数

			if (rows == 0 || rows < 1||columns == 0 || columns < 1) {
				errorInfo.add("导入的文件内容为空");
			}

			int needCols = columns;
			// 表头正式开始

			ExcelSheetData sheetData=new ExcelSheetData(errorInfo,requiredColunmIndex,validator);

			// 正式开始
			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {// 行
				String[] rowItem = new String[needCols];
				for (int columnIndex = 0; columnIndex < needCols; columnIndex++) { // 列
					String value =tools.getValue(sheet, columnIndex, rowIndex);
					rowItem[columnIndex] = Tools.isEmpty(value) ? null : value;
				}

				if (rowIndex == 0) {
					if (!fixedNames.isEmpty() && !Arrays.asList(rowItem).equals(fixedNames)) {
						errorInfo.add("文件格式不正确，表头列名不正确");
						throw new HrmsException("文件格式不正确", ListArrayUtil.class);
					}
				}
				sheetData.addRowData(rowItem);
			}
			sheetData.checkValidate();
			return sheetData.getData();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
				}
			}
			if (!errorInfo.isEmpty()) {
				throw new HrmsException(StringUtils.join(errorInfo.iterator(), messageSeparator), ListArrayUtil.class);
			}
		}
	}

	private static class ExcelSheetData{
		private int[] requiredColunmIndex;
		private List errorInfo;
		private Stack data=new Stack();
		private RowDataValidator validator;

		public ExcelSheetData(List errorInfo,int[] requiredColunmIndex,RowDataValidator validator) {
			this.errorInfo=errorInfo;
			this.validator=validator;
			this.requiredColunmIndex=requiredColunmIndex;
		}

		public void addRowData(String[] rowItem){
			data.push(rowItem);
		}

		public void deleteEmptyRows(){
			String[] last=(String[])data.lastElement();
			while (isAllElementEmtpy(last)) {
				data.remove(last);
				last=(String[])data.lastElement();
			}
		}

		private boolean isAllElementEmtpy(String[] row){
			for (int i = 0; i < row.length; i++) {
				if(!Tools.isEmpty(row[i]))return false;
			}
			return true;
		}

		/**
		 * 检查中间的空值
		 */
		public void checkValidate(){
			deleteEmptyRows();
			for (int i = 0; i < data.size(); i++) {
				int rowIndex=i+1;
				String[] row=(String[])data.get(i);
				if(isAllElementEmtpy(row)){
					errorInfo.add("第" + rowIndex + "行整行为空值");
				}else{
					for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
						String value = row[columnIndex];
						if (ArrayUtils.contains(requiredColunmIndex, columnIndex)&& Tools.isEmpty(value)) {
							errorInfo.add("第" +rowIndex+ "行第" + (columnIndex + 1) + "列:为空值");
						}
					}
					validator.isValidate(row, errorInfo, rowIndex);
				}
			}
		}

		public List getData(){
			return data;
		}
	}


	/**
	 * 将excel表格中的数据导入List中，异常消息默认以<br>
	 * 分割，默认不进行列名、是否为空验证，并且不验证行数据的正确性
	 *
	 * @param path
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArray(String path) throws HrmsException {
		return importDataToListArray(path, null, null);
	}

	/**
	 * 将excel表格中的数据导入List中，异常消息默认以<br>
	 * 分割，默认不验证行数据的正确性
	 *
	 * @param path
	 *            excel地址
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArray(String path, String[] fixedColumnNames, Integer[] requiredColunmIndex) throws HrmsException {
		return importDataToListArray(path, fixedColumnNames, requiredColunmIndex, new RowDataValidator() {
			public boolean isValidate(String[] rowItem, List errorInfo, int rowIndex) {
				return true;
			}
		});
	}

	/**
	 * 将excel表格中的数据导入List中，异常消息默认以<br>
	 * 分割
	 *
	 * @param path
	 *            excel地址
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @param validator
	 *            验证行数据的正确性
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArray(String path, String[] fixedColumnNames, Integer[] requiredColunmIndex, RowDataValidator validator)
			throws HrmsException {
		return importDataToListArray(path, fixedColumnNames,ArrayUtils.toPrimitive(requiredColunmIndex), validator, "<br>");
	}

	/**
	 * 将excel表格中的数据导入List中，异常消息默认以<br>
	 * 分割
	 *
	 * @param path
	 *            excel地址
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @param validator
	 *            验证行数据的正确性
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArray(String path, String[] fixedColumnNames, int[] requiredColunmIndex, RowDataValidator validator)
			throws HrmsException {
		return importDataToListArray(path, fixedColumnNames,requiredColunmIndex, validator, "<br>");
	}

	/**
	 * 将excel表格中的数据导入List中，异常消息默认以<br>
	 * 分割
	 *
	 * @param path
	 *            excel地址
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @param validator
	 *            验证行数据的正确性
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArray(String path, ExcelValidateRules rules)throws HrmsException {
		return importDataToListArray(path,rules.getFixedColumnNames(),rules.getRequiredColunmIndex(),rules,rules.getMessageSeparator());
	}

	/**
	 * 将excel表格中的数据导入List中，异常消息默认以<br>
	 * 分割
	 *
	 * @param path
	 *            excel地址
	 * @param fixedColumnNames
	 *            要导入的列名
	 * @param requiredColunmIndex
	 *            值不能为空的列
	 * @param validator
	 *            验证行数据的正确性
	 * @return
	 * @throws HrmsException
	 */
	public static List importDataToListArrayByJxl(String path, ExcelValidateRules rules)throws HrmsException {
		return importDataToListArrayByJxl(path,rules.getFixedColumnNames(),rules.getRequiredColunmIndex(),rules,rules.getMessageSeparator());
	}

	/**
	 * 将行映射为map
	 *
	 * @param rows
	 *            行数据
	 * @param keyIndex
	 *            键所在的数组索引
	 * @param valueIndex
	 *            map中value所在的索引， 如果valueIndex长度大于1，按照索引收集数组中的数据生成一个新数组作为value值，
	 *            如果valueIndex只有一个索引，则直接将数组中的该索引数据作为value，
	 *            如果valueIndex为null或者长度为1，则将整行数据作为value值
	 * @return
	 */
	public static Map rowsToMap(List rows, int keyIndex, int[] valueIndex) {
		Map dict = new HashMap();
		Iterator iter = rows.iterator();
		iter.next();// 跳过表头
		while (iter.hasNext()) {
			String[] row = (String[]) iter.next();
			String key = row[keyIndex];
			Object value = DEFAULT_ROWDATA_MAPPER.map(row, valueIndex);
			dict.put(key, value);
		}
		return dict;
	}

	/**
	 * 过滤数据，对行和列两个纬度进行数据过滤
	 *
	 * @param rows
	 * @param filter
	 * @param valueIndex
	 * @return
	 */
	public static List filterRow(List rows, ObjectReducer reducer, int[] valueIndex) {
		List result = new ArrayList();
		Iterator iter = rows.iterator();
		iter.next();// 跳过表头
		while (iter.hasNext()) {
			String[] row = (String[]) iter.next();
			if (reducer.reduce(row)) {
				result.add(DEFAULT_ROWDATA_MAPPER.map(row, valueIndex));
			}
		}
		return result;
	}

	/**
	 * 查找第一个匹配条件的数据，如果未找到返回null
	 *
	 * @param rows
	 * @param filter
	 * @param valueIndex
	 * @return
	 */
	public static Object firstMatch(List rows, ObjectReducer reducer, int[] valueIndex) {
		Iterator iter = rows.iterator();
		iter.next();// 跳过表头
		while (iter.hasNext()) {
			String[] row = (String[]) iter.next();
			if (reducer.reduce(row)) {
				return DEFAULT_ROWDATA_MAPPER.map(row, valueIndex);
			}
		}
		return null;
	}

	public static interface Filter {
		boolean isMatch(Object item);
	}

	/**
	 * 集合对象过滤器，将不满足条件的对象从迭代集合中移除，只用于可修改集合，否则会报错
	 *
	 * @param iter
	 * @param filter
	 */
	public static void filter(Iterator iter, Filter filter) {
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			if (!filter.isMatch(object))iter.remove();
		}
	}

	/**
	 * 集合对象过滤器，将不满足条件的对象从集合中移除，只用于可修改集合，否则会报错
	 *
	 * @param coll
	 * @param filter
	 */
	public static void filter(Collection coll, Filter filter) {
		if (!Tools.isEmpty(coll)) {
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				Object object = (Object) iter.next();
				if (!filter.isMatch(object))iter.remove();
			}
		}
	}

	/**
	 * 将对象数组中的对象过滤到一个新的集合中
	 *
	 * @param coll
	 * @param mapper
	 * @return
	 */
	public static List reduce(Object[] coll, ObjectReducer reducer) {
		if (!Tools.isEmpty(coll)) {
			return reduce(Arrays.asList(coll), reducer);
		}
		return new ArrayList();
	}

	/**
	 * 将集合中的对象过滤到一个新的集合中
	 *
	 * @param coll
	 * @param mapper
	 * @return
	 */
	public static List reduce(Collection coll, ObjectReducer reducer) {
		List result = new ArrayList();
		if (!Tools.isEmpty(coll)) {
			Iterator iter = coll.iterator();
			return reduce(iter, reducer);
		}
		return result;
	}

	/**
	 * 将迭代器中的对象过滤到一个新的集合中
	 *
	 * @param iter
	 * @param mapper
	 * @return
	 */
	public static List reduce(Iterator iter, ObjectReducer reducer) {
		List result = new ArrayList();
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			if(reducer.reduce(object)){
				result.add(object);
			}
		}
		return result;
	}


	/**
	 * 将迭代器中的对象过滤到一个新的集合中
	 *
	 * @param iter
	 * @param mapper
	 * @return
	 */
	public static List reduce(Enumeration enumeration, ObjectReducer reducer) {
		List result = new ArrayList();
		while (enumeration.hasMoreElements()) {
			Object object = (Object) enumeration.nextElement();
			if(reducer.reduce(object)){
				result.add(object);
			}
		}
		return result;
	}

	/**
	 * 将对象数组中的对象属性映射为一个集合
	 *
	 * @param coll
	 * @param mapper
	 * @return
	 */
	public static List map(Object[] coll, ObjectMapper mapper) {
		if (!Tools.isEmpty(coll)) {
			return map(Arrays.asList(coll), mapper);
		}
		return new ArrayList();
	}

	public static List map(Collection coll,final String prop){
		return map(coll,new ObjectMapper() {
			public Object map(Object target) {
				return Tools.getPropValue(target, prop);
			}
		});
	}

	public static List mapAsText(Collection coll,final String prop){
		return map(coll,new ObjectMapper() {
			public Object map(Object target) {
				return Tools.getPropText(target, prop);
			}
		});
	}




	/**
	 * 将集合中的对象属性映射为一个集合
	 *
	 * @param coll
	 * @param mapper
	 * @return
	 */
	public static List map(Collection coll, ObjectMapper mapper) {
		List result = new ArrayList();
		if (!Tools.isEmpty(coll)) {
			Iterator iter = coll.iterator();
			return map(iter, mapper);
		}
		return result;
	}

	/**
	 * 将迭代器中的对象属性映射为一个集合
	 *
	 * @param iter
	 * @param mapper
	 * @return
	 */
	public static List map(Iterator iter, ObjectMapper mapper) {
		List result = new ArrayList();
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			Object mapped=mapper.map(object);
			if(mapped!=null){
				result.add(mapped);
			}
		}
		return result;
	}

	/**
	 * 判断符合条件的对象是否存在集合中
	 *
	 * @param coll
	 * @param reducer
	 * @return
	 */
	public static boolean contain(Object[] coll, ObjectReducer reducer) {
		if (Tools.isEmpty(coll))return false;
		for (int i = 0; i < coll.length; i++) {
			Object object = coll[i];
			if (reducer.reduce(object))return true;
		}
		return false;
	}

	/**
	 * 判断符合条件的对象是否存在集合中
	 *
	 * @param coll
	 * @param reducer
	 * @return
	 */
	public static boolean contain(Collection coll, ObjectReducer reducer) {
		if (Tools.isEmpty(coll))return false;
		return contain(coll.iterator(), reducer);
	}

	/**
	 * 判断符合条件的对象是否存在集合中
	 *
	 * @param iter
	 * @param reducer
	 * @return
	 */
	public static boolean contain(Iterator iter, ObjectReducer reducer) {
		if (iter == null) return false;
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			if (reducer.reduce(object))return true;
		}
		return false;
	}


	/**
	 * 判断符合条件的对象是否存在集合中
	 *
	 * @param coll
	 * @param reducer
	 * @return
	 */
	public static Object forEach(Object[] coll, ObjectCollector collector) {
		if (Tools.isEmpty(coll))return null;
		for (int i = 0; i < coll.length; i++) {
			collector.collect(coll[i]);
		}
		return collector.result();
	}

	/**
	 * 判断符合条件的对象是否存在集合中
	 *
	 * @param coll
	 * @param reducer
	 * @return
	 */
	public static Object forEach(Collection coll, ObjectCollector collector) {
		if (Tools.isEmpty(coll))return null;
		return forEach(coll.iterator(), collector);
	}

	/**
	 * 判断符合条件的对象是否存在集合中
	 *
	 * @param iter
	 * @param reducer
	 * @return
	 */
	public static Object forEach(Iterator iter, ObjectCollector collector) {
		if (iter == null) return null;
		while (iter.hasNext()) {
			collector.collect(iter.next());
		}
		return collector.result();
	}

	/**
	 * 先映射然后化简
	 *
	 * @param coll
	 * @param mapReduce
	 * @return
	 */
	public static List mapReduce(Object[] coll, MapReduce mapReduce) {
		if (!Tools.isEmpty(coll)) {
			return mapReduce(Arrays.asList(coll), mapReduce);
		}
		return new ArrayList();
	}

	/**
	 * 先映射然后化简
	 *
	 * @param coll
	 * @param mapReduce
	 * @return
	 */
	public static List mapReduce(Collection coll, MapReduce mapReduce) {
		if (!Tools.isEmpty(coll)) {
			return mapReduce(coll.iterator(), mapReduce);
		}
		return new ArrayList();
	}

	/**
	 * 先映射然后化简
	 *
	 * @param iter
	 * @param mapReduce
	 * @return
	 */
	public static List mapReduce(Iterator iter, MapReduce mapReduce) {
		List result = new ArrayList();
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			Object mapped = mapReduce.map(object);
			if (mapReduce.reduce(mapped)) {
				result.add(mapped);
			}
		}
		return result;
	}

	/**
	 * 先化简然后映射
	 *
	 * @param coll
	 * @param mapReduce
	 * @return
	 */
	public static List reduceMap(Object[] coll, MapReduce mapReduce) {
		if (!Tools.isEmpty(coll)) {
			return reduceMap(Arrays.asList(coll), mapReduce);
		}
		return new ArrayList();
	}

	/**
	 * 先化简然后映射
	 *
	 * @param coll
	 * @param mapReduce
	 * @return
	 */
	public static List reduceMap(Collection coll, MapReduce mapReduce) {
		if (!Tools.isEmpty(coll)) {
			return reduceMap(coll.iterator(), mapReduce);
		}
		return new ArrayList();
	}

	/**
	 * 先化简然后映射
	 *
	 * @param iter
	 * @param mapReduce
	 * @return
	 */
	public static List reduceMap(Iterator iter, MapReduce mapReduce) {
		List result = new ArrayList();
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			if (mapReduce.reduce(object)) {
				result.add(mapReduce.map(object));
			}
		}
		return result;
	}


	/**
	 * 先化简然后映射
	 *
	 * @param iter
	 * @param mapReduce
	 * @return
	 */
	public static List reduceMap(Iterator iter, ObjectMapper mapper,ObjectReducer reducer) {
		List result = new ArrayList();
		while (iter.hasNext()) {
			Object object = (Object) iter.next();
			if (reducer.reduce(object)) {
				result.add(mapper.map(object));
			}
		}
		return result;
	}

	/**
	 * 先化简然后映射
	 *
	 * @param iter
	 * @param mapReduce
	 * @return
	 */
	public static List reduceMap(Enumeration enumeration, ObjectMapper mapper,ObjectReducer reducer) {
		List result = new ArrayList();
		while (enumeration.hasMoreElements()) {
			Object object = (Object) enumeration.nextElement();
			if (reducer.reduce(object)) {
				result.add(mapper.map(object));
			}
		}
		return result;
	}

	/**
	 * 将两个字符串数组合并
	 *
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static String[] combine(String[] array1, String[] array2) {
		int length = 0;
		String[] result = new String[array1.length + array2.length];

		for (int i = 0; i < array1.length; i++) {
			result[length++] = array1[i];
		}
		for (int i = 0; i < array2.length; i++) {
			result[length++] = array2[i];
		}
		return result;
	}

	/**
	 * 合并多个数组
	 *
	 * @param array1
	 * @return
	 */
	public static String[] combine(String[][] array1) {
		int length = 0;
		for (int i = 0; i < array1.length; i++) {
			length+=array1[i].length;
		}
		int index=0;
		String[] result = new String[length];
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array1[i].length; j++) {
				result[index++]=array1[i][j];
			}
		}
		return result;
	}

	/**
	 * 将两个字符串数组合并
	 *
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static String[] combine(String item, String[] array2) {
		return combine(new String[]{item}, array2);
	}

	public static List paddingLeft(List listArray,int startIndex,String[] array){
		return padding(listArray, startIndex, true, array);
	}

	public static List paddingLeft(List listArray,int startIndex,String item){
		return padding(listArray, startIndex, true, new String[]{item});
	}

	public static List paddingLeft(List listArray,String item){
		return padding(listArray, 0, true, new String[]{item});
	}

	/**
	 * 根据条件重新生成一个ListArray
	 *
	 * @param listArray  原始数据
	 * @param startIndex listArray的起始Index
	 * @param isLeft	   是否向左padding
	 * @param array		   要padding的数据
	 * @return
	 */
	public static List padding(List listArray,int startIndex,boolean isLeft,String[] array){
		List details = new ArrayList(listArray.size());
		for (int i = startIndex; i < listArray.size(); i++) {
			String[] row = (String[]) listArray.get(i);
			String [] newRow=isLeft?ListArrayUtil.combine(array, row):ListArrayUtil.combine(row,array);
			details.add(newRow);
		}
		return details;
	}



	public static String join(Iterator iter,String separator,final String prefix,final String suffix){
		return join(map(iter, new ObjectMapper() {
			public Object map(Object target) {
				return prefix+target+suffix;
			}
		}),separator);
	}
	public static String join(Collection coll,String separator,final String prefix,final String suffix){
		return join(coll.iterator(),separator,prefix,suffix);
	}
	public static String join(Object[] coll,String separator,final String prefix,final String suffix){
		return join(Arrays.asList(coll),separator,prefix,suffix);
	}

	public static String join(Iterator iter,String separator){
		return StringUtils.join(iter,separator);
	}

	public static String join(Collection coll,String prop,String seperator){
		return join(mapAsText(coll, prop),seperator);
	}

	public static String join(Collection coll,String separator){
		if(Tools.isEmpty(coll))return "";
		return join(coll.iterator(),separator);
	}
	public static String join(Object[] coll,String separator){
		if(Tools.isEmpty(coll))return "";
		return join(Arrays.asList(coll),separator);
	}

	/**
	 * 计算data中的合计，data中必须保存为Map，且Map的value值必须为数字
	 *
	 * @param data
	 * @return
	 */
	public static Map totalOfItems(Collection data,Collection summaryColumns){
		return totalOfItems(data.iterator(),summaryColumns);
	}

	public static String totalOfItems(Collection data,String summaryColumn){
		return totalOfItems(data.iterator(), summaryColumn);
	}

	public static String totalOfItems(Iterator dataIter,String summaryColumn){
		String total=Tools.ZERO.toString();
		while (dataIter.hasNext()) {
			Object item =dataIter.next();
			String subValue=Tools.getPropText(item, summaryColumn);
			total=MathExtend.add(total,Tools.filterNullToZero(subValue));
		}
		return total;
	}

	public static Map totalOfItems(Iterator dataIter,Collection numericKeys){
		return totalOfItems(dataIter, new Collection[]{numericKeys});
	}

	public static Map totalOfItems(Iterator dataIter,Collection[] numericKeys){
		Map total=new HashMap();
		while (dataIter.hasNext()) {
			Object item =dataIter.next();
			for (int i = 0; i < numericKeys.length; i++) {
				Iterator keyIter=numericKeys[i].iterator();
				while (keyIter.hasNext()) {
					String key = (String) keyIter.next();
					accumulateValueTo(total, key.toLowerCase(), item);
				}
			}
		}
		return total;
	}

	public static Map totalOfItems(Iterator dataIter,String[] numericKeys){
		return totalOfItems(dataIter, new String[][]{numericKeys});
	}

	public static Map totalOfItems(Iterator dataIter,String[][] numericKeys){
		Map total=new HashMap();
		while (dataIter.hasNext()) {
			Object item =dataIter.next();
			for (int i = 0; i < numericKeys.length; i++) {
				for (int j = 0; j < numericKeys[i].length; j++) {
					String key=numericKeys[i][j];
					accumulateValueTo(total, key, item);
				}
			}
		}
		return total;
	}

	private  static void accumulateValueTo(Map total,String key,Object source){
		String totalValue=total.get(key)+"";
		String subValue=Tools.getPropText(source, key);
		String value=MathExtend.add(Tools.filterNullToZero(totalValue),Tools.filterNullToZero(subValue));
		total.put(key, value);
	}


	/**
	 * 将集合对象映射到map
	 *
	 * @param source 要映射的集合
	 * @param key 集合对象中作为map的key的属性名称
	 * @param value 集合对象中作为map的value的属性名称，{@linkplain com.icitic.hrms.util.Tools.REF_SELF}为引用集合对象自身
	 * @return
	 */
	public static Map toMap(Collection source,String key,String value){
		Map result=new HashMap();
		if(Tools.isNotEmpty(source)){
			Iterator iter=source.iterator();
			while (iter.hasNext()) {
				Object item = (Object) iter.next();
				result.put(Tools.getPropValue(item, key), Tools.REF_SELF.equals(value)?item:Tools.getPropValue(item, value));
			}
		}
		return result;
	}

	/**
	 * 将集合对象映射到map，map中的value为引用集合对象自身
	 *
	 * @param source 要映射的集合
	 * @param prop 集合对象中作为map的key的属性名称
	 * @return
	 */
	public static Map toSelfRefMap(Collection source,String prop){
		return toMap(source, prop, Tools.REF_SELF);
	}

	public static boolean contain(String[] coll,String item){
		return Arrays.asList(coll).contains(item);
	}

	public static boolean notIn(String[] coll,String item){
		return !contain(coll, item);
	}

	public static boolean containAll(String[] coll,String[] items){
		return Arrays.asList(coll).containsAll(Arrays.asList(items));
	}

	public static Object[] singleton(Object target){
		return new Object[]{target};
	}

	/**
	 * 删除字符串数组中特定的字符串
	 *
	 * @param coll
	 * @param removed
	 * @return
	 */
	public static String[] remove(String[] coll,String removed){
		List list=new ArrayList();
		for (int i = 0; i < coll.length; i++) {
			if(!StringUtils.equals(removed, coll[i]))list.add(coll[i]);
		}
		return toStringArray(list);
	}

	/**
	 * 删除字符串数组中特定的字符串
	 *
	 * @param coll
	 * @param removed
	 * @return
	 */
	public static String[] removeAll(String[] coll,String[] removed){
		List removedItems=Arrays.asList(removed);
		List list=new ArrayList();
		for (int i = 0; i < coll.length; i++) {
			if(!removedItems.contains(coll[i])){
				list.add(coll[i]);
			}
		}
		return toStringArray(list);
	}

	/**
	 *
	 * 将字符串集合转换为字符串数组
	 *
	 * @param coll
	 * @return
	 */
	public static String[] toStringArray(Collection coll){
		if (coll == null) {
			return null;
		}
		return (String[])coll.toArray(new String[coll.size()]);
	}
}