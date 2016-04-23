package cn.accessbright.blade.core.utils;

import cn.accessbright.blade.core.utils.collections.Arrays;
import cn.accessbright.blade.core.utils.collections.Collections;
import cn.accessbright.blade.core.utils.collections.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/4/18.
 */
public abstract class Strings {

    private Strings() {
    }

    /**
     * 可验证String、StringBuffer、StringBuilder等实现了CharSequence接口的类<br>
     * 是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        if (cs == null) return true;
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String trim(String str) {
        if (str == null)
            return str;
        return str.trim();
    }

    public static String toString(Object target) {
        if (target == null) return "";
        if (target instanceof String)
            return (String) target;
        if (target instanceof Object[])
            return join((Object[]) target, ",");
        return target.toString();
    }

    public static String toString(Object target, String defaultValue) {
        String str = toString(target);
        if (isEmpty(str))
            return defaultValue;
        return str;
    }

    public static boolean notEquals(String from, String target) {
        return !equals(from, target);
    }

    public static boolean equals(String from, String target) {
        if (from == null || target == null) return false;
        return from.equals(target);
    }

    public static boolean equalsIgnoreCase(String from, String target) {
        if (from == null || target == null) return false;
        return from.equalsIgnoreCase(target);
    }


    /**
     * 根据逗号,冒号：空格来分割字符串
     *
     * @param str
     * @return
     */
    public static String[] split(String str) {
        if (isEmpty(str)) return new String[]{};
        return str.split("[,:\\s]+");
    }


    public static String repeatChars(int count, char c) {
        String digits = "";
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                digits += c;
            }
        }
        return digits;
    }


    public static <T> String join(Iterator<T> iter, String separator, final String prefix, final String suffix) {
        return join(Collections.map(iter, new ObjectMapper<String, T>() {
            public String map(T target) {
                return prefix + target + suffix;
            }
        }), separator);
    }

    public static String join(Collection coll, String separator, final String prefix, final String suffix) {
        return join(coll.iterator(), separator, prefix, suffix);
    }

    public static <T> String join(T[] coll, String separator, final String prefix, final String suffix) {
        return join(Arrays.asList(coll), separator, prefix, suffix);
    }

    public static String join(Iterator<?> iter, String separator) {
        return StringUtils.join(iter, separator);
    }

    public static String join(Collection coll, String prop, String seperator) {
        return join(Collections.mapAsText(coll, prop), seperator);
    }

    public static String join(Collection<?> coll, String separator) {
        if (Collections.isEmpty(coll)) return "";
        return join(coll.iterator(), separator);
    }

    public static <T> String join(T[] coll, String separator) {
        if (Arrays.isEmpty(coll)) return "";
        return join(Arrays.asList(coll), separator);
    }


    public static boolean isNumber(String number) {
        if (isEmpty(number)) return false;
        try {
            Double.parseDouble(number.replaceAll(",", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEmptyOrNumber(String number) {
        return isEmpty(number) || isNumber(number);
    }

    /**
     * 查询非数字的索引，未找到则返回-1
     *
     * @param numbers
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int findNonEmptyOrNumber(String[] numbers, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            String number = numbers[i];
            if (!isEmpty(number) && !isNumber(number)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEmptyOrNumber(String[] numbers, int startIndex, int endIndex) {
        return findNonEmptyOrNumber(numbers, startIndex, endIndex) == -1;
    }

    public static String upperCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String lowerCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }
}
