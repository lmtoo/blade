package cn.accessbright.blade.core.utils.collections;

import java.util.Enumeration;

/**
 * 空的枚举器
 *
 * @author ll
 */
public class EmptyEnumeration<T> implements Enumeration<T> {
    public static Enumeration<?> INSTANCE = new EmptyEnumeration<Object>();

    private EmptyEnumeration() {
    }

    public boolean hasMoreElements() {
        return false;
    }

    public T nextElement() {
        return null;
    }
}