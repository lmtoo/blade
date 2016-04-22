package cn.accessbright.blade.core.utils.web;

import cn.accessbright.blade.core.utils.ClassUtils;
import cn.accessbright.blade.core.utils.Objects;
import cn.accessbright.blade.core.utils.Strings;
import cn.accessbright.blade.core.utils.collections.Collections;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/18.
 */
public class WebUtils {

    /**
     * 通过用户的请求获取用户的ip地址和主机信息
     *
     * @param request 用户发送的请求
     * @return String 返回类型  （地址跳转参数）
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (Strings.isEmpty(ipAddress)) {
            if ("unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("Proxy-Client-IP");
            else if ("unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            else if ("unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            else if ("unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("HTTP_CLIENT_IP");
            else if ("unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }


    /**
     * 根据参数前缀和属性名称创建对象
     *
     * @param request request对象
     * @param targetClass 要创建的对象类型
     * @param paramPrefix 参数前缀
     * @param index 若为数组参数，index为该对象所在数组索引，否则传小于0的数字表示不为数组参数
     * @param propNames 要创建对象的属性名称
     * @return
     */
    protected <T> T buildObjectByParam(ServletRequest request, Class<T> targetClass, String paramPrefix, int index, String[] propNames) {
        String seperator = index < 0 ? "." : ("[" + index + "].");
        String[] propValues = new String[propNames.length];
        for (int i = 0; i < propNames.length; i++) {
            propValues[i] = request.getParameter(paramPrefix + seperator + propNames[i]);
        }

        T target = null;
        if (!Collections.isAllEmpty(propValues)) {
            try {
                target = targetClass.newInstance();
                for (int i = 0; i < propNames.length; i++) {
                    if (propValues[i] != null) {
                        Objects.setPropValue(target, propNames[i], propValues[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    /**
     * ���ݲ���ǰ׺�Ͷ���������������ָ��Class�Ķ���
     *
     * @param request
     * @param targetClass Ҫ����Ķ���Class
     * @param paramPrefix
     * @param propNames
     * @return
     */
    protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix, String[] propNames) {
        Map<String,String> params=new HashMap<>();
        return buildObjectByParam(request, targetClass, paramPrefix, -1, propNames);
    }

    /**
     * ���ݲ���ǰ׺�������Ͷ���������������ָ��Class�Ķ���
     *
     * @param request
     * @param targetClass
     * @param paramPrefix
     * @param index
     * @return
     */
    protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix, int index) {
        String[] propNames = ClassUtils.getPropNames(targetClass);
        return buildObjectByParam(request, targetClass, paramPrefix, index, propNames);
    }

    /**
     * ���ݲ���ǰ׺�Ͷ���������������ָ��Class�Ķ���
     *
     * @param request
     * @param targetClass Ҫ����Ķ���Class
     * @param paramPrefix
     * @return
     */
    protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix) {
        return buildObjectByParam(request, targetClass, paramPrefix, -1);
    }

    protected Map findParamValueByPrefix(HttpServletRequest request, String prefix) {
        Map data = new HashMap();
        Enumeration enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String paramName = (String) enumer.nextElement();
            if (paramName.startsWith(prefix)) {
                data.put(paramName.substring(prefix.length()), request.getParameter(paramName));
            }
        }
        return data;
    }

    /**
     * 创建数组参数对象
     *
     * @param request
     * @param targetClass
     * @param paramPrefix
     * @param <T>
     * @return
     */
    protected <T> List<T> buildObjectListByParam(HttpServletRequest request, Class<T> targetClass, String paramPrefix) {
        List<T> validatableList = new ArrayList<T>();
        int size = findMaxListSizeByParamPrefix(request.getParameterNames(), paramPrefix);
        for (int i = 0; i < size; i++) {
            T builded = (T)buildObjectByParam(request, targetClass, paramPrefix, i);
            validatableList.add(builded);
        }
        return validatableList;
    }

    /**
     * 查询参数中指定前缀的最大索引
     *
     * @param paramNames
     * @param paramPrefix
     * @return
     */
    public static int findMaxListSizeByParamPrefix(Enumeration<String> paramNames, String paramPrefix) {
        Pattern pattern = Pattern.compile("^" + paramPrefix + "\\[(\\d+?)\\]\\.\\w+$");
        int size = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            Matcher matcher = pattern.matcher(paramName);
            if (matcher.find()) {
                String index = matcher.group(1);
                size = Math.max(size, Integer.parseInt(index) + 1);
            }
        }
        return size;
    }
}
