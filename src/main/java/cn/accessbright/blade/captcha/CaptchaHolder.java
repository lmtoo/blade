package cn.accessbright.blade.captcha;

/**
 * 存放验证码的缓存接口，默认提供了基于session和HashMap的实现<br>
 * 如何上述两种实现都不能满足需求，用户可以实现此接口，来自定义验证码存放的位置<br>
 *
 * captcha holder，default provided session based implementation，<br>
 * user can implement this interface to implements other based implements<br>
 *
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