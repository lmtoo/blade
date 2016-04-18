package cn.accessbright.blade.captcha;


import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collections;
import java.util.Map;

/**
 *  存放验证码的容器，基于HttpSession实现
 *
 * Created by Administrator on 2016/4/13.
 */
@Component("sessionCaptchaHolder")
public class SessionCaptchaHolder implements CaptchaHolder {
    @Override
    public String put(String captchaKey, String captchaValue) {
        String oldValue = get(captchaKey);

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Map<String, String> captchaKeyValue = Collections.singletonMap(captchaKey, captchaValue);
        requestAttributes.setAttribute(CAPTCHA_CACHE_KEY, captchaKeyValue, RequestAttributes.SCOPE_SESSION);

        return oldValue;
    }

    @Override
    public String get(String captchaKey) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Object beforeValue = requestAttributes.getAttribute(CAPTCHA_CACHE_KEY, RequestAttributes.SCOPE_SESSION);

        if (beforeValue != null) {
            Map<String, String> beforeKeyValue = (Map<String, String>) beforeValue;
            return beforeKeyValue.get(captchaKey);
        }
        return null;
    }

    @Override
    public String remove(String captchaKey) {
        String oldValue = get(captchaKey);
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.removeAttribute(CAPTCHA_CACHE_KEY, RequestAttributes.SCOPE_SESSION);
        return oldValue;
    }

    @Override
    public void clear() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.removeAttribute(CAPTCHA_CACHE_KEY, RequestAttributes.SCOPE_SESSION);
    }
}
