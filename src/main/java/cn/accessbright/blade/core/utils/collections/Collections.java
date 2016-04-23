package cn.accessbright.blade.core.utils.collections;

import cn.accessbright.blade.core.utils.ClassUtils;
import cn.accessbright.blade.core.utils.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/19.
 */
public class Collections {

    public static <T> boolean isEmpty(Collection<T> coll) {
        if (coll == null) return true;
        return coll.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> coll) {
        return !isEmpty(coll);
    }


    public static <K, V> boolean isEmpty(Map<K, V> dict) {
        if (dict == null) return true;
        return dict.isEmpty();
    }


    /**
     * 将对象数组中的对象属性映射为一个集合
     *
     * @param coll
     * @param mapper
     * @return
     */
    public static <R, T> List<R> map(T[] coll, ObjectMapper<R, T> mapper) {
        if (!Arrays.isEmpty(coll)) {
            return map(java.util.Arrays.asList(coll), mapper);
        }
        return new ArrayList<R>();
    }

    public static <R, T> List<R> map(Collection<T> coll, final String prop, Class<R> propClazz) {
        return map(coll, new ObjectMapper<R, T>() {
            public R map(T target) {
                return (R) Objects.getPropValue(target, prop);
            }
        });
    }

    public static <T> List map(Collection<T> coll, final String prop) {
        return map(coll, prop, Object.class);
    }

    public static <T> List<String> mapAsText(Collection<T> coll, final String prop) {
        return map(coll, new ObjectMapper<String, T>() {
            public String map(T target) {
                return Objects.getPropText(target, prop);
            }
        });
    }


    /**
     * 集合对象过滤器，将不满足条件的对象从集合中移除，只用于可修改集合，否则会报错
     *
     * @param coll
     * @param filter
     */
    public static <T> void filter(Collection<T> coll, ObjectFilter<T> filter) {
        if (Collections.isNotEmpty(coll))
            filter(coll.iterator(), filter);
    }


    /**
     * 将对象数组中的对象过滤到一个新的集合中
     *
     * @param coll
     * @param filter
     * @return
     */
    public static <T> List<T> filter(T[] coll, ObjectFilter<T> filter) {
        if (!Arrays.isEmpty(coll)) {
            return reduce(java.util.Arrays.asList(coll), filter);
        }
        return java.util.Collections.emptyList();
    }

    /**
     * 将集合中的对象过滤到一个新的集合中
     *
     * @param coll
     * @param filter
     * @return
     */
    public static <T> List<T> reduce(Collection<T> coll, ObjectFilter<T> filter) {
        List<T> result = new ArrayList<T>();
        if (!Collections.isEmpty(coll)) {
            Iterator<T> iter = coll.iterator();
            return filter(iter, filter);
        }
        return result;
    }

    /**
     * 将迭代器中的对象过滤到一个新的集合中
     *
     * @param iter
     * @param filter
     * @return
     */
    public static <T> List<T> filter(Iterator<T> iter, ObjectFilter<T> filter) {
        List<T> result = new ArrayList<T>();
        while (iter.hasNext()) {
            T object = iter.next();
            if (filter.isMatch(object)) {
                result.add(object);
            }
        }
        return result;
    }


    /**
     * 将迭代器中的对象过滤到一个新的集合中
     *
     * @param enumeration
     * @param filter
     * @return
     */
    public static <T> List<T> filter(Enumeration<T> enumeration, ObjectFilter<T> filter) {
        List<T> result = new ArrayList<T>();
        while (enumeration.hasMoreElements()) {
            T item = enumeration.nextElement();
            if (filter.isMatch(item)) {
                result.add(item);
            }
        }
        return result;
    }


    /**
     * 将集合中的对象属性映射为一个集合
     *
     * @param coll
     * @param mapper
     * @return
     */
    public static <R, T> List<R> map(Collection<T> coll, ObjectMapper<R, T> mapper) {
        List<R> result = new ArrayList<R>();
        if (!Collections.isEmpty(coll)) {
            return map(coll.iterator(), mapper);
        }
        return result;
    }

    /**
     * 将迭代器中的对象属性映射为一个集合
     *
     * @param iter
     * @param mapper
     * @return
     */
    public static <R, T> List<R> map(Iterator<T> iter, ObjectMapper<R, T> mapper) {
        List<R> result = new ArrayList<R>();
        while (iter.hasNext()) {
            R mapped = mapper.map(iter.next());
            if (mapped != null) {
                result.add(mapped);
            }
        }
        return result;
    }

    /**
     * 判断符合条件的对象是否存在集合中
     *
     * @param coll
     * @param filter
     * @return
     */
    public static <T> boolean contain(T[] coll, ObjectFilter<T> filter) {
        if (Arrays.isEmpty(coll)) return false;
        for (int i = 0; i < coll.length; i++) {
            if (filter.isMatch(coll[i])) return true;
        }
        return false;
    }

    /**
     * 判断符合条件的对象是否存在集合中
     *
     * @param coll
     * @param filter
     * @return
     */
    public static <T> boolean contain(Collection<T> coll, ObjectFilter<T> filter) {
        if (isEmpty(coll)) return false;
        return contain(coll.iterator(), filter);
    }

    /**
     * 判断符合条件的对象是否存在集合中
     *
     * @param iter
     * @param filter
     * @return
     */
    public static <T> boolean contain(Iterator<T> iter, ObjectFilter<T> filter) {
        if (iter == null) return false;
        while (iter.hasNext()) {
            if (filter.isMatch(iter.next())) return true;
        }
        return false;
    }


    /**
     * 判断符合条件的对象是否存在集合中
     *
     * @param coll
     * @param collector
     * @return
     */
    public static <R, T> R forEach(T[] coll, ObjectCollector<R, T> collector) {
        if (Arrays.isEmpty(coll)) return null;
        for (int i = 0; i < coll.length; i++) {
            collector.collect(coll[i]);
        }
        return collector.result();
    }

    /**
     * 判断符合条件的对象是否存在集合中
     *
     * @param coll
     * @param collector
     * @return
     */
    public static <R, T> R forEach(Collection<T> coll, ObjectCollector<R, T> collector) {
        if (isEmpty(coll)) return null;
        return forEach(coll.iterator(), collector);
    }

    /**
     * 判断符合条件的对象是否存在集合中
     *
     * @param iter
     * @param collector
     * @return
     */
    public static <R, T> R forEach(Iterator<T> iter, ObjectCollector<R, T> collector) {
        if (iter == null) return null;
        while (iter.hasNext()) {
            collector.collect(iter.next());
        }
        return collector.result();
    }

    /**
     * 先映射然后化简
     *
     * @param coll
     * @param mapReduce
     * @return
     */
    public static <R, T> R mapReduce(T[] coll, MapReduce<R, T> mapReduce) {
        if (!Arrays.isEmpty(coll)) {
            return mapReduce(java.util.Arrays.asList(coll), mapReduce);
        }
        return null;
    }

    /**
     * 先映射然后化简
     *
     * @param coll
     * @param mapReduce
     * @return
     */
    public static <R, T> R mapReduce(Collection<T> coll, MapReduce<R, T> mapReduce) {
        if (isNotEmpty(coll)) {
            return mapReduce(coll.iterator(), mapReduce);
        }
        return null;
    }

    /**
     * 先映射然后化简
     *
     * @param iter
     * @param mapReduce
     * @return
     */
    public static <R, T> R mapReduce(Iterator<T> iter, MapReduce<R, T> mapReduce) {
        R result = null;
        while (iter.hasNext()) {
            T item = iter.next();
            R mapped = mapReduce.map(item);
            if (result != null) {
                result = mapReduce.reduce(result, mapped);
            } else {
                result = mapped;
            }
        }
        return result;
    }


    public <T> Map<String, T> findValueByPrefix(Map<String, T> params, String prefix) {
        Map data = new HashMap();
        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (key.startsWith(prefix)) {
                data.put(key.substring(prefix.length()), params.get(key));
            }
        }
        return data;
    }


    /**
     * 根据参数前缀和属性名称创建对象
     *
     * @param params      所有的参数
     * @param targetClass 要创建的对象类型
     * @param prefix      参数前缀
     * @param propNames   要创建对象的属性名称
     * @return
     */
    public static <T> T buildObjectByPrefix(Map<String, Object> params, Class<T> targetClass, String prefix, String[] propNames) {
        if (!ClassUtils.hasDefaultConstructor(targetClass)) return null;

        String keyPrefix = prefix + ".";
        Object[] propValues = new Object[propNames.length];
        for (int i = 0; i < propNames.length; i++) {
            propValues[i] = params.get(keyPrefix + propNames[i]);
        }

        T target = null;
        if (!Arrays.isAllNull(propValues)) {
            target = ClassUtils.newDefaultInstance(targetClass);
            if (target == null) return null;
            for (int i = 0; i < propNames.length; i++) {
                if (propValues[i] != null) {
                    Objects.setPropValue(target, propNames[i], propValues[i]);
                }
            }
        }
        return target;
    }


    public static <E, K, V> Map<K, V> toMap(Collection<E> coll, String key, String value) {
        Map<K, V> result = new HashMap<>();
        if (Collections.isNotEmpty(coll)) {
            Iterator<E> iter = coll.iterator();
            while (iter.hasNext()) {
                E item = iter.next();
                result.put(Objects.getPropertyValue(item, key), Objects.getPropertyValue(item, value));
            }
        }
        return result;
    }


    public static <K, E> Map<K, E> toMap(Collection<E> coll, String key) {
        Map<K, E> result = new HashMap<>();
        if (Collections.isNotEmpty(coll)) {
            Iterator<E> iter = coll.iterator();
            while (iter.hasNext()) {
                E item = iter.next();
                result.put(Objects.getPropertyValue(item, key), item);
            }
        }
        return result;
    }
}
