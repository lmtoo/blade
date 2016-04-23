package cn.accessbright.blade.captcha;

import java.io.OutputStream;

/**
 * 验证码服务，需要依赖验证码缓存接口（在{@code AbstractCaptchaService}中依赖），使用方式<br>
 *
 * 1、根据验证码存放的key，生成验证码图片<br>
 *
 *  byte[] image = captchaService.generateImage(captchaKey);<br>
 *
 *  2、根据用户输入的验证码和验证码存放的key，来验证用户输入的验证码是否正确<br>
 *
 *  boolean isValid=captchaService.validate(captchaKey, "1234");<br>
 *
 * Created by Administrator on 2016/4/13.
 */
public interface CaptchaService {

    /**
     * 生成验证码图片
     *
     * @param captchaKey
     * @return
     */
    byte[] generateImage(String captchaKey);

    /**
     * 将验证码图片写入到指定的输出流
     *
     * @param captchaKey
     * @param output
     */
    void writeImageTo(String captchaKey, OutputStream output);

    /**
     * 验证提供的验证码是否匹配
     *
     * @param captchaKey
     * @param captchaValue
     * @return
     */
    boolean validate(String captchaKey, String captchaValue);
}
