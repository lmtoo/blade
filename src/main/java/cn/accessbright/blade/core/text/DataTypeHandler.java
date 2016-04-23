package cn.accessbright.blade.core.text;

/**
 * 数据处理器
 * 
 * @author ll
 * 
 */
public interface DataTypeHandler {
	/**
	 * 处理数据
	 *
	 * @param param
	 *            参数
	 * @param value
	 *            被处理的值
	 * @return
	 */
	Object handle(String param, Object value);
}