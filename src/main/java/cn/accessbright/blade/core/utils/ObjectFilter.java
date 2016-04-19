package cn.accessbright.blade.core.utils;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface ObjectFilter<T> {
    boolean isMatch(T target);
}
