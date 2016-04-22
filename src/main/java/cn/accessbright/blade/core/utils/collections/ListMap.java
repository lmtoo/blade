package cn.accessbright.blade.core.utils.collections;

import java.util.HashMap;

import org.apache.commons.collections.map.ListOrderedMap;

/**
 * User: kangshu
 * Date: 2006-8-19
 * Time: 11:05:05
 */
public class ListMap extends ListOrderedMap {
    public ListMap() {
        super(new HashMap());
    }
}
