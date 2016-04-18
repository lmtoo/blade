package cn.accessbright.blade.core;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 将Iterator适配到Enumeration
 * 
 * @author ll
 *
 */
public class IteratorEnumeration implements Enumeration {
	private Iterator iterator;

	public IteratorEnumeration() {
		super();
	}

	public IteratorEnumeration(Iterator iterator) {
		this.iterator = iterator;
	}

	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	public Object nextElement() {
		return iterator.next();
	}

	public Iterator getIterator() {
		return iterator;
	}

	public void setIterator(Iterator iterator) {
		this.iterator = iterator;
	}
}