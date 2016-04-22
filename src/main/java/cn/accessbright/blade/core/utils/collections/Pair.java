package cn.accessbright.blade.core.utils.collections;


import cn.accessbright.blade.core.utils.Strings;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * 一对儿
 *
 * @author ll
 */
public class Pair<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public V setValue(V value) {
        return this.value = value;
    }

    public V getValue() {
        return value;
    }

    public K getLeft() {
        return key;
    }

    public V getRight() {
        return value;
    }

    public void setLeft(K left) {
        this.key = left;
    }

    public void setRight(V right) {
        this.value = right;
    }

    public String getLeftStr() {
        return Strings.toString(key);
    }

    public String getRightStr() {
        return Strings.toString(value);
    }

    public boolean isKey(K otherKey) {
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