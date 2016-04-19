package cn.accessbright.blade.core.utils;

/**
 * 访问者模式，遍历聚合对象，并将最终结果返回
 *
 * @param <R>
 * @param <T>
 */
public interface ObjectCollector<R, T> {
    void collect(T target);

    R result();
}
