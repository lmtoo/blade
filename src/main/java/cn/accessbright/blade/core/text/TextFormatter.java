package cn.accessbright.blade.core.text;

import cn.accessbright.blade.core.Tools;
import cn.accessbright.blade.core.text.typehandler.TypeHandlerServiceLocator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串格式化<br>
 * 格式如：${field:type:param}
 *
 * @author ll
 */
public class TextFormatter {
    public static Pattern PLACE_HOLDER_PATTERN = Pattern.compile("\\$\\{(\\w+)(?:\\:(\\w+)(?:\\:(.+))?)?\\}");

    /**
     * 格式化给定的字符串
     *
     * @param pattern
     * @param data
     * @return
     */
    public static String format(String pattern, Object data) {
        if (!isPattern(pattern))
            return pattern;
        Matcher matcher = PLACE_HOLDER_PATTERN.matcher(pattern);
        StringBuffer message = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            String type = matcher.group(2);
            String param = matcher.group(3);
            String value = getValue(data, key, type, param);
            matcher.appendReplacement(message, Tools.filterNullToStr(value));
        }
        matcher.appendTail(message);
        return message.toString();
    }

    public static String getValue(Object data, String prop, String type, String param) {
        // 只接受一级嵌套
        int dotIndex = prop.indexOf('.');
        if (dotIndex > -1) {
            String[] props = prop.split("\\.");
            String[] types = type.split("\\.");

            Object retValue = data;
            TypeHandlerServiceLocator serviceLoactor = TypeHandlerServiceLocator.getInstance();
            for (int i = 0; i < props.length; i++) {
                String propValue = Tools.getPropText(retValue, props[i]);
                String currentType = i > types.length - 1 ? null : types[i];
                retValue = serviceLoactor.getHandler(currentType).handle(i == (props.length - 1) ? param : null, propValue);
            }
            return retValue.toString();
        } else {
            String propValue = Tools.getPropText(data, prop);
            TypeHandlerServiceLocator serviceLoactor = TypeHandlerServiceLocator.getInstance();
            return serviceLoactor.getHandler(type).handle(param, propValue).toString();
        }
    }

    /**
     * 判断是否为格式
     *
     * @param pattern
     * @return
     */
    public static boolean isPattern(String pattern) {
        Matcher matcher = PLACE_HOLDER_PATTERN.matcher(pattern);
        return matcher.find();
    }
}

