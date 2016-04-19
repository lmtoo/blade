package cn.accessbright.blade.core.utils;

/**
 * 对象属性过滤器，对对象中的某几个属性进行过滤，作用与ColumnDataFilter类似
 * 不过ObjectMapper主要映射对象，而ColumnDataFilter主要映射数组
 *
 * @author ll
 */
public interface ObjectMapper<R, T> {
    R map(T target);
}