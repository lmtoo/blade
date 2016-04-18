package cn.accessbright.blade.core;

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
            return ListArrayUtil.join((Object[]) target, ",");
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
}
