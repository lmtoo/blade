package cn.accessbright.blade.core.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public static BigDecimal toNumber(String num) {
        return Strings.isEmpty(num) ? BigDecimal.ZERO : new BigDecimal(num);
    }

    public static BigDecimal toNumber(Number num) {
        return new BigDecimal(((Number) num).toString());
    }

    public static BigDecimal toNumber(Object num) {
        if (num == null) return BigDecimal.ZERO;
        if (num instanceof BigDecimal) {
            return (BigDecimal) num;
        } else if (num instanceof Number) {
            return toNumber((Number) num);
        } else if (num instanceof String) {
            return toNumber((String) num);
        }
        return new BigDecimal(Strings.toString(num));
    }

    public static String toNumberString(String num) {
        return toNumber(num).toString();
    }

    public static String toNumberString(Number num, int scale) {
        return toNumber(num).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String toNumberString(String num, int scale) {
        return toNumber(num).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 计算data中的合计，data中必须保存为Map，且Map的value值必须为数字
     *
     * @param dataIter
     * @param keys
     * @param <E>
     * @return
     */
    public static <E> Map<String, BigDecimal> totalOfItems(Iterable<E> dataIter, Iterable<String> keys) {
        Map<String, BigDecimal> total = new HashMap<>();
        for (E item : dataIter) {
            for (String key : keys) {
                BigDecimal totalValue = total.get(key);
                Object subValue = Objects.getPropValue(item, key);
                totalValue = toNumber(subValue).add(totalValue);
                total.put(key, totalValue);
            }
        }
        return total;
    }

    public static <E> Map<String, BigDecimal> totalOfItems(Iterable<E> dataIter, String[] keys) {
        return totalOfItems(dataIter, Arrays.asList(keys));
    }
}
