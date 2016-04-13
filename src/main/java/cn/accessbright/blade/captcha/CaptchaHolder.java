package cn.accessbright.blade.captcha;

/**
 * captcha holder，default provided session based implementation，<br>
 * user can implement this interface to implements other based implements
 * <p>
 * Created by Administrator on 2016/4/13.
 */
public interface CaptchaHolder {

    /**
     * 验证码所在缓存区id
     */
    String CAPTCHA_CACHE_KEY = "CAPTCHA_CACHE_KEY";

    /**
     * 将验证码放入缓存
     * @param captchaKey
     * @param captchaValue
     * @return
     */
    String put(String captchaKey, String captchaValue);

    /**
     * 获取验证码
     * @param captchaKey
     * @return
     */
    String get(String captchaKey);

    /**
     * 删除验证码
     * @param captchaKey
     * @return
     */
    String remove(String captchaKey);

    /**
     * 清空验证码
     *
     */
    void clear();
}