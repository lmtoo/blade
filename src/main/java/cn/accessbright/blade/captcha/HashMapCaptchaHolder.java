package cn.accessbright.blade.captcha;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  存放验证码的缓存，基于HashMap实现<br>
 * 不建议在生产环境中使用，仅限于单元测试
 * <p>
 * Created by Administrator on 2016/4/13.
 */
public class HashMapCaptchaHolder implements CaptchaHolder {

    private static Map<String, String> internalHolder = Collections.synchronizedMap(new HashMap<String, String>());

    @Override
    public String put(String captchaKey, String captchaValue) {
        return internalHolder.put(captchaKey, captchaValue);
    }

    @Override
    public String get(String captchaKey) {
        return internalHolder.get(captchaKey);
    }

    @Override
    public String remove(String captchaKey) {
        return internalHolder.remove(captchaKey);
    }

    @Override
    public void clear() {
        internalHolder.clear();
    }
}
