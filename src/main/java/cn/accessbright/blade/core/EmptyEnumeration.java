package cn.accessbright.blade.core;

import java.util.Enumeration;

/**
 * 空的枚举器
 * 
 * @author ll
 * 
 */
public class EmptyEnumeration implements Enumeration {
	public static Enumeration INSTANCE = new EmptyEnumeration();

	private EmptyEnumeration() {
	}

	public boolean hasMoreElements() {
		return false;
	}

	public Object nextElement() {
		return null;
	}
}