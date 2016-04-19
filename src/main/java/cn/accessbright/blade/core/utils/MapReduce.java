package cn.accessbright.blade.core.utils;

/**
 * 映射化简
 *
 * @param <R>  最终的累积结果
 * @param <T> 目标元素
 */
public interface MapReduce<R, T> extends ObjectMapper<R, T>, ObjectReducer<R> {
}
