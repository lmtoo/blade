package cn.accessbright.blade.core.utils.collections;

import cn.accessbright.blade.core.utils.collections.ObjectMapper;
import cn.accessbright.blade.core.utils.collections.ObjectReducer;

/**
 * 映射化简
 *
 * @param <R>  最终的累积结果
 * @param <T> 目标元素
 */
public interface MapReduce<R, T> extends ObjectMapper<R, T>, ObjectReducer<R> {
}
