package cn.accessbright.blade.core.utils.collections;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface ObjectFilter<T> {
    boolean isMatch(T target);
}
