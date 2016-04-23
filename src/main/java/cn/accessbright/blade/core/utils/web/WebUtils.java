package cn.accessbright.blade.core.utils.web;

import cn.accessbright.blade.core.utils.ClassUtils;
import cn.accessbright.blade.core.utils.Strings;
import cn.accessbright.blade.core.utils.collections.Arrays;
import cn.accessbright.blade.core.utils.collections.Collections;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param request     request对象
     * @param targetClass 要创建的对象类型
     * @param paramPrefix 参数前缀
     * @param index       若为数组参数，index为该对象所在数组索引，否则传小于0的数字表示不为数组参数
     * @param propNames   要创建对象的属性名称
     * @return
     */
    public static <T> T buildObjectByParam(ServletRequest request, Class<T> targetClass, String paramPrefix, int index, String[] propNames) {
        String prefix = paramPrefix + (index < 0 ? "" : ("[" + index + "]"));
        return Collections.buildObjectByPrefix(paramToMap(request, prefix), targetClass, paramPrefix, propNames);
    }


    /**
     * 根据参数前缀和对象属性名，构造指定Class的对象
     *
     * @param request
     * @param targetClass 要构造的对象Class
     * @param paramPrefix
     * @param propNames
     * @return
     */
    public static Object buildObjectByParam(ServletRequest request, Class targetClass, String paramPrefix, String[] propNames) {
        Map<String, String> params = new HashMap<>();
        return buildObjectByParam(request, targetClass, paramPrefix, -1, propNames);
    }

    /**
     * 根据参数前缀、索引和对象属性名，构造指定Class的对象
     *
     * @param request
     * @param targetClass
     * @param paramPrefix
     * @param index
     * @return
     */
    public static Object buildObjectByParam(ServletRequest request, Class targetClass, String paramPrefix, int index) {
        String[] propNames = ClassUtils.getPropNames(targetClass);
        return buildObjectByParam(request, targetClass, paramPrefix, index, propNames);
    }

    /**
     * 根据参数前缀和对象属性名，构造指定Class的对象
     *
     * @param request
     * @param targetClass 要构造的对象Class
     * @param paramPrefix
     * @return
     */
    public static Object buildObjectByParam(ServletRequest request, Class targetClass, String paramPrefix) {
        return buildObjectByParam(request, targetClass, paramPrefix, -1);
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
    public static <T> List<T> buildObjectListByParam(ServletRequest request, Class<T> targetClass, String paramPrefix) {
        List<T> buildedList = new ArrayList<T>();
        int size = Arrays.getSizeByPrefix(request.getParameterNames(), paramPrefix);
        for (int i = 0; i < size; i++) {
            T builded = (T) buildObjectByParam(request, targetClass, paramPrefix, i);
            buildedList.add(builded);
        }
        return buildedList;
    }


    /**
     * 获取请求参数
     *
     * @param request
     * @param prefix
     * @return
     */
    public static Map<String, Object> subparamToMap(ServletRequest request, String prefix) {
        Map<String, Object> data = new HashMap<>();
        Enumeration enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String paramName = (String) enumer.nextElement();
            if (paramName.startsWith(prefix)) {
                String[] paramValues = request.getParameterValues(paramName);
                data.put(paramName.substring(prefix.length()), paramValues.length > 1 ? paramValues : paramValues[0]);
            }
        }
        return data;
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> paramToMap(ServletRequest request, String prefix) {
        Map<String, Object> paramMapping = new HashMap<>();
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String paramName = enumer.nextElement();
            if (paramName.startsWith(prefix)) {
                String[] paramValues = request.getParameterValues(paramName);
                paramMapping.put(paramName, paramValues.length > 1 ? paramValues : paramValues[0]);
            }
        }
        return paramMapping;
    }


    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> paramToMap(ServletRequest request) {
        Map<String, Object> paramMapping = new HashMap<>();
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String paramName = enumer.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            paramMapping.put(paramName, paramValues.length > 1 ? paramValues : paramValues[0]);
        }
        return paramMapping;
    }


    public static String getRequestValue(ServletRequest request, String key) {
        String ret = request.getParameter(key);
        if (Strings.isNotEmpty(ret)) {
            return ret;
        }
        return (String) request.getAttribute(key);
    }
}
