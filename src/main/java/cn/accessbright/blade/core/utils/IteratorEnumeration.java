package cn.accessbright.blade.core.utils;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 将Iterator适配到Enumeration
 *
 * @author ll
 */
public class IteratorEnumeration<T> implements Enumeration<T> {
    private Iterator<T> iterator;

    public IteratorEnumeration() {
    }

    public IteratorEnumeration(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    public T nextElement() {
        return iterator.next();
    }

    public Iterator<T> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }
}