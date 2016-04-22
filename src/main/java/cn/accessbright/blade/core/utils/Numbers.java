package cn.accessbright.blade.core.utils;

/**
 * Created by Administrator on 2016/4/22.
 */
public class Numbers {

    public static double parseDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static double parseDouble(String str) {
        return parseDouble(str, 0);
    }
}
