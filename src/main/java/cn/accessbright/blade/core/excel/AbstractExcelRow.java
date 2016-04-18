package cn.accessbright.blade.core.excel;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.icitic.hrms.sys.pojo.bo.InfoItemBO;
import com.icitic.hrms.util.CodeUtil;
import com.icitic.hrms.util.Tools;

/**
 * 
 * @author ll
 *
 */
public abstract class AbstractExcelRow implements ExcelRow {
	protected int row;
	protected int column;

	public AbstractExcelRow(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public ExcelRow addString(String value) throws RuntimeException {
		addString(value, TEXT_ALIGN_LEFT);
		return this;
	}
	
	public ExcelRow addString(String[] props, Object target) {
		for (int i = 0; i < props.length; i++) {
			addString(props[i], target);
		}
		return this;
	}
	
	public ExcelRow addString(String prop, Object target) {
		return addString(Tools.getPropText(target, prop));
	}

	public ExcelRow addString(Object value) throws RuntimeException {
		addString(Tools.toString(value));
		return this;
	}
	
	public ExcelRow addString(int value) {
		return addString(value+"");
	}

	public ExcelRow addString(String[] values) throws RuntimeException {
		addString(values, TEXT_ALIGN_LEFT);
		return this;
	}
	
	public ExcelRow addString(Object[] values) throws RuntimeException {
		addString(values, 0);
		return this;
	}
	
	public ExcelRow addString(Object[] values, int start) {
		addString(values,start,values.length);
		return this;
	}
	
	public ExcelRow addString(Object[] values, int start, int lenth) {
		for (int i = start; i < lenth; i++) {
			addString(Tools.toString(values[i]), TEXT_ALIGN_LEFT);
		}
		return this;
	}
	

	public ExcelRow addString(List values) throws RuntimeException {
		addString(values, TEXT_ALIGN_LEFT);
		return this;
	}

	public ExcelRow addString(String key, Map values) throws RuntimeException {
		addString(key, values, TEXT_ALIGN_LEFT);
		return this;
	}

	public ExcelRow addString(String key, Map values, boolean ignoreKeyCase) throws RuntimeException {
		addString(key, values, ignoreKeyCase, TEXT_ALIGN_LEFT);
		return this;
	}

	public ExcelRow addString(List keys, Map values) throws RuntimeException {
		addString(keys, values, TEXT_ALIGN_LEFT);
		return this;
	}
	
	public ExcelRow addString(String[] keys, Map values) {
		return addString(Arrays.asList(keys),values);
	}

	public ExcelRow addString(List keys, Map values, boolean ignoreKeyCase) {
		addString(keys, values, ignoreKeyCase, TEXT_ALIGN_LEFT);
		return this;
	}

	public ExcelRow addString(String value, int align) {
		addString(column++, row, value, align);
		return this;
	}

	public ExcelRow addString(String[] values, int align) {
		for (int i = 0; i < values.length; i++) {
			addString(values[i], align);
		}
		return this;
	}

	public ExcelRow addString(List values, int align) {
		Iterator iter = values.iterator();
		while (iter.hasNext()) {
			addString((String) iter.next(), align);
		}
		return this;
	}

	public ExcelRow addString(String key, Map values, int align) {
		addString(key, values, false, align);
		return this;
	}

	public ExcelRow addString(String key, Map values, boolean ignoreKeyCase, int align) {
		String theKey = key;
		if (ignoreKeyCase) {
			String upperKey = key.toUpperCase();
			String lowerKey = key.toLowerCase();
			theKey = values.containsKey(lowerKey) ? lowerKey : upperKey;
		}
		addString((String) values.get(theKey), align);
		return this;
	}

	public ExcelRow addString(List keys, Map values, int align) {
		addString(keys, values, true, align);
		return this;
	}

	public ExcelRow addString(List keys, Map values, boolean ignoreKeyCase, int align) {
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			addString(key, values, ignoreKeyCase, align);
		}
		return this;
	}

	public ExcelRow addNumber(double value) {
		addNumber(value, 2);
		return this;
	}

	public ExcelRow addNumber(String value) throws RuntimeException {
		addNumber(Double.parseDouble(Tools.filterNullToZero(value)));
		return this;
	}
	
	public ExcelRow addNumber(Object value) throws RuntimeException {
		addNumber(Tools.toString(value));
		return this;
	}
	
	public ExcelRow addNumber(Object[] values) {
		addNumber(values,0,values.length);
		return this;
	}
	
	public ExcelRow addNumber(Object[] values, int begin) {
		addNumber(values, begin, values.length-begin);
		return this;
	}
	
	public ExcelRow addNumber(Object[] values, int begin, int length) {
		for (int i = begin; i < begin+length; i++) {
			addNumber(values[i]);
		}
		return this;
	}

	public ExcelRow addNumber(double value, int digits) {
		addNumber(column++, row, value, digits);
		return this;
	}

	public ExcelRow addNumber(String value, int digits) {
		addNumber(Double.parseDouble(Tools.filterNullToZero(value)), digits);
		return this;
	}

	public ExcelRow addNumber(String key, Map values) throws RuntimeException {
		addNumber(key, values, false);
		return this;
	}

	public ExcelRow addNumber(String key, Map values, boolean ignoreKeyCase, int digits) throws RuntimeException {
		String theKey = key;
		if (ignoreKeyCase) {
			String upperKey = key.toUpperCase();
			String lowerKey = key.toLowerCase();
			theKey = values.containsKey(lowerKey) ? lowerKey : upperKey;
		}
		addNumber((String) values.get(theKey), digits);
		return this;
	}

	public ExcelRow addNumber(List keys, Map values, int digits) throws RuntimeException {
		addNumber(keys, values, false, digits);
		return this;
	}

	public ExcelRow addNumber(List keys, Map values, boolean ignoreKeyCase, int digits) throws RuntimeException {
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			addNumber(key, values, ignoreKeyCase, digits);
		}
		return this;
	}

	public ExcelRow addNumber(String key, Map values, int digits) throws RuntimeException {
		addNumber(key, values, false, digits);
		return this;
	}

	public ExcelRow addNumber(String key, Map values, boolean ignoreKeyCase) throws RuntimeException {
		String theKey = key;
		if (ignoreKeyCase) {
			String upperKey = key.toUpperCase();
			String lowerKey = key.toLowerCase();
			theKey = values.containsKey(lowerKey) ? lowerKey : upperKey;
		}
		addNumber((String) values.get(theKey));
		return this;
	}

	public ExcelRow addNumber(List keys, Map values) throws RuntimeException {
		addNumber(keys, values, false);
		return this;
	}
	
	public ExcelRow addNumber(String[] keys, Map values) {
		return addNumber(Arrays.asList(keys), values);
	}

	public ExcelRow addNumber(List keys, Map values, boolean ignoreKeyCase) throws RuntimeException {
		return addNumber(keys.iterator(), values, ignoreKeyCase);
	}
	
	public ExcelRow addNumber(Iterator keysIter, Map values) {
		return addNumber(keysIter, values, false);
	}
	
	public ExcelRow addNumber(Iterator keysIter, Map values, boolean ignoreKeyCase) {
		while (keysIter.hasNext()) {
			String key = (String) keysIter.next();
			addNumber(key, values, ignoreKeyCase);
		}
		return this;
	}

	public ExcelRow addInteger(int value) {
		addNumber(value, 0);
		return this;
	}

	public ExcelRow addInteger(Integer value) {
		addInteger(value.intValue());
		return this;
	}

	public ExcelRow addInteger(List keys, Map values) {
		addInteger(keys, values, false);
		return this;
	}

	public ExcelRow addInteger(List keys, Map values, boolean ignoreKeyCase) {
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			addInteger(key, values, ignoreKeyCase);
		}
		return this;
	}

	public ExcelRow addInteger(String key, Map values) {
		addInteger(key, values, false);
		return this;
	}

	public ExcelRow addInteger(String key, Map values, boolean ignoreKeyCase) {
		String theKey = key;
		if (ignoreKeyCase) {
			String upperKey = key.toUpperCase();
			String lowerKey = key.toLowerCase();
			theKey = values.containsKey(lowerKey) ? lowerKey : upperKey;
		}
		addInteger((String) values.get(theKey));
		return this;
	}

	public ExcelRow addInteger(String value) {
		Double num = new Double(Tools.filterNullToZero(value));
		addInteger(num.intValue());
		return this;
	}

	public ExcelRow addCode(String value, String codeType) throws RuntimeException {
		addString(CodeUtil.interpertCode(codeType, value));
		return this;
	}
	
	public ExcelRow addCode(String key, Map values, String codeType) {
		return addCode(key, values, codeType, true);
	}
	
	public ExcelRow addCode(String[] keys, Map values, String codeType) {
		for (int i = 0; i < keys.length; i++) {
			addCode(keys[i], values, codeType);
		}
		return this;
	}
	
	public ExcelRow addCode(String key, Map values, String codeType, boolean ignoreKeyCase) {
		if (ignoreKeyCase) {
			String upperKey = key.toUpperCase();
			String lowerKey = key.toLowerCase();
			key = values.containsKey(lowerKey) ? lowerKey : upperKey;
		}
		return addCode((String) values.get(key),codeType);
	}
	
	public ExcelRow addCode(String[] keys, Map values, String codeType, boolean ignoreKeyCase) {
		for (int i = 0; i < keys.length; i++) {
			addCode(keys[i], values, codeType, ignoreKeyCase);
		}
		return this;
	}
	
	public ExcelRow addCode(Object value, String codeType) {
		return addCode(value+"", codeType);
	}

	public ExcelRow addCode(InfoItemBO info, String value) throws RuntimeException {
		String dataType = info.getItemDataType();
		if (InfoItemBO.DATA_TYPE_INT.equals(dataType)) {
			addString(value);
		} else if (InfoItemBO.DATA_TYPE_FLOAT.equals(dataType)) {
			addNumber(value);
		} else if (InfoItemBO.DATA_TYPE_STRING.equals(dataType)) {
			addString(value);
		} else if (InfoItemBO.DATA_TYPE_REMARK.equals(dataType)) {
			addString(value);
		} else if (InfoItemBO.DATA_TYPE_DATE.equals(dataType)) {
			addString(value);
		} else if (InfoItemBO.DATA_TYPE_CODE.equals(dataType)) {
			addCode(value, info.getItemCodeSet());
		} else {
			addCode(value, InfoItemBO.getHtmlDataType(info.getItemDataType()));
		}
		return this;
	}

	public ExcelRow addCode(InfoItemBO info, Map values, boolean ignoreKeyCase) throws RuntimeException {
		String theKey = info.getItemId();
		if (ignoreKeyCase) {
			String upperKey = theKey.toUpperCase();
			String lowerKey = theKey.toLowerCase();
			theKey = values.containsKey(lowerKey) ? lowerKey : upperKey;
		}
		addCode(info, (String) values.get(theKey));
		return this;
	}

	public ExcelRow addCode(List infos, Map values, boolean ignoreKeyCase) throws RuntimeException {
		Iterator iter = infos.iterator();
		while (iter.hasNext()) {
			InfoItemBO info = (InfoItemBO) iter.next();
			addCode(info, values, ignoreKeyCase);
		}
		return this;
	}

	public ExcelRow mergeString(int from, int to, String value) throws RuntimeException {
		mergeString(from, to, value, TEXT_ALIGN_CENTER);
		return this;
	}

	public ExcelRow mergeString(int from, int to, String value, int align) {
		column = to + 1;
		mergeString(from, to, row, value, align);
		return this;
	}

	public ExcelRow mergeString(int length, String value) throws RuntimeException {
		mergeString(length, value, TEXT_ALIGN_CENTER);
		return this;
	}

	public ExcelRow mergeString(int length, String value, int align) {
		mergeString(column, column + length - 1, row, value, align);
		column += length;
		return this;
	}

	protected abstract ExcelRow addString(int column, int row, String value, int align);

	protected abstract ExcelRow addNumber(int column, int row, double value, int digits);

	protected abstract ExcelRow mergeString(int from, int to, int row, String value, int align);
}