package cn.accessbright.blade.core.utils.collections;

import cn.accessbright.blade.core.utils.collections.EmptyEnumeration;
import cn.accessbright.blade.core.utils.IteratorEnumeration;

import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 有序的properties，properties的key迭代时按照读取文件的顺序迭代
 * 
 * @author ll
 */
public class ListableProperties extends Properties {
	private Map innerProperties = new LinkedHashMap();

	public int size() {
		return innerProperties.size();
	}

	public boolean isEmpty() {
		return innerProperties.isEmpty();
	}

	public boolean containsKey(Object key) {
		return innerProperties.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return innerProperties.containsValue(value);
	}

	public void putAll(Map m) {
		innerProperties.putAll(m);
	}

	public void clear() {
		innerProperties.clear();
	}

	public Set keySet() {
		return innerProperties.keySet();
	}

	public Collection values() {
		return innerProperties.values();
	}

	public Set entrySet() {
		return innerProperties.entrySet();
	}

	public boolean equals(Object o) {
		return innerProperties.equals(o);
	}

	public int hashCode() {
		return innerProperties.hashCode();
	}

	public String getProperty(String key, String defaultValue) {
		String val = getProperty(key);
		return (val == null) ? defaultValue : val;
	}

	public String getProperty(String key) {
		return (String) innerProperties.get(key);
	}

	public Object setProperty(String key, String value) {
		return put(key, value);
	}

	public Enumeration keys() {
		if (innerProperties.isEmpty()) {
			return EmptyEnumeration.INSTANCE;
		} else {
			return new IteratorEnumeration(innerProperties.keySet().iterator());
		}
	}

	public Enumeration propertyNames() {
		return keys();
	}

	public synchronized Enumeration elements() {
		if (innerProperties.isEmpty()) {
			return EmptyEnumeration.INSTANCE;
		} else {
			return new IteratorEnumeration(innerProperties.values().iterator());
		}
	}

	public synchronized Object get(Object key) {
		return innerProperties.get(key);
	}

	public synchronized Object remove(Object key) {
		return innerProperties.remove(key);
	}

	public synchronized boolean contains(Object value) {
		return innerProperties.containsValue(value);
	}

	public synchronized Object put(Object key, Object value) {
		return innerProperties.put(key, value);
	}
}