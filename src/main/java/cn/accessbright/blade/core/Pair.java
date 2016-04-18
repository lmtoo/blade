package cn.accessbright.blade.core;

import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.icitic.hrms.util.Tools;

/**
 * 一对儿
 * 
 * @author ll
 * 
 */
public class Pair implements Map.Entry {
	private Object key;
	private Object value;

	public Pair(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

	public Object setValue(Object value) {
		return this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public Object getLeft() {
		return key;
	}

	public Object getRight() {
		return value;
	}

	public void setLeft(Object left) {
		this.key = left;
	}

	public void setRight(Object right) {
		this.value = right;
	}
	
	public String getLeftStr(){
		return Tools.toString(key);
	}
	
	public String getRightStr(){
		return Tools.toString(value);
	}

	public boolean isKey(Object otherKey) {
		if (this.key == otherKey)
			return true;
		if (this.key == null)
			return false;
		return this.key.equals(otherKey);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof Pair) {
			Pair other = (Pair) obj;
			return this.key.equals(other.key) && this.value.equals(other.value);
		}
		return false;
	}
}