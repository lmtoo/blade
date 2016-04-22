package cn.accessbright.blade.core.utils.collections;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface ObjectReducer<R> {
    R reduce(R accumulate, R target);
}
