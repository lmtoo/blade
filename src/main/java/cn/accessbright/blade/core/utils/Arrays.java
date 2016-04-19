package cn.accessbright.blade.core.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class Arrays {

    public static boolean contain(String[] coll, String item) {
        return java.util.Arrays.asList(coll).contains(item);
    }

    public static boolean notIn(String[] coll, String item) {
        return !contain(coll, item);
    }

    public static boolean containAll(String[] coll, String[] items) {
        return java.util.Arrays.asList(coll).containsAll(java.util.Arrays.asList(items));
    }

    public static <T> T[] singleton(T target) {
        T[] array = (T[]) Array.newInstance(target.getClass(), 1);
        array[0] = target;
        return array;
    }

    /**
     * 删除字符串数组中特定的字符串
     *
     * @param coll
     * @param removed
     * @return
     */
    public static <T> T[] remove(T[] coll, T removed) {
        List<T> list = new ArrayList<T>(java.util.Arrays.asList(coll));
        list.remove(removed);
        return toArray(list);
    }

    /**
     * 删除字符串数组中特定的字符串
     *
     * @param coll
     * @param removed
     * @return
     */
    public static <T> T[] removeAll(T[] coll, T[] removed) {
        List<T> removedItems = java.util.Arrays.asList(removed);
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < coll.length; i++) {
            if (!removedItems.contains(coll[i])) {
                list.add(coll[i]);
            }
        }
        return toArray(list);
    }

    /**
     * 将字符串集合转换为字符串数组
     *
     * @param coll
     * @return
     */
    public static <T> T[] toArray(Collection<T> coll) {
        if (coll == null) {
            return null;
        }
        return (T[]) coll.toArray();
    }


    /**
     * 将两个字符串数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static String[] combine(String[] array1, String[] array2) {
        int length = 0;
        String[] result = new String[array1.length + array2.length];

        for (int i = 0; i < array1.length; i++) {
            result[length++] = array1[i];
        }
        for (int i = 0; i < array2.length; i++) {
            result[length++] = array2[i];
        }
        return result;
    }

    /**
     * 合并多个数组
     *
     * @param array1
     * @return
     */
    public static String[] combine(String[][] array1) {
        int length = 0;
        for (int i = 0; i < array1.length; i++) {
            length += array1[i].length;
        }
        int index = 0;
        String[] result = new String[length];
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1[i].length; j++) {
                result[index++] = array1[i][j];
            }
        }
        return result;
    }

    /**
     * 将两个字符串数组合并
     *
     * @param item
     * @param array2
     * @return
     */
    public static String[] combine(String item, String[] array2) {
        return combine(new String[]{item}, array2);
    }


    /**
     * 根据条件重新生成一个ListArray
     *
     * @param listArray  原始数据
     * @param startIndex listArray的起始Index
     * @param isLeft     是否向左padding
     * @param array      要padding的数据
     * @return
     */
    public static List<String[]> padding(List<String[]> listArray, int startIndex, boolean isLeft, String[] array) {
        List<String[]> details = new ArrayList<String[]>(listArray.size());
        for (int i = startIndex; i < listArray.size(); i++) {
            String[] row = listArray.get(i);
            String[] newRow = isLeft ? Arrays.combine(array, row) : Arrays.combine(row, array);
            details.add(newRow);
        }
        return details;
    }


    public static List<String[]> paddingLeft(List<String[]> listArray, int startIndex, String[] array) {
        return padding(listArray, startIndex, true, array);
    }

    public static List<String[]> paddingLeft(List<String[]> listArray, int startIndex, String item) {
        return padding(listArray, startIndex, true, new String[]{item});
    }

    public static List<String[]> paddingLeft(List<String[]> listArray, String item) {
        return padding(listArray, 0, true, new String[]{item});
    }
}
