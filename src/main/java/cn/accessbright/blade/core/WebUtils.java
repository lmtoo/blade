package cn.accessbright.blade.core;

import javax.servlet.http.HttpServletRequest;

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
}
