package cn.accessbright.blade.captcha;

import java.io.OutputStream;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface CaptchaService {

    /**
     * 生成验证码的key，用来生成验证码和验证验证码是否正确
     *
     * @return
     */
    String generateKey();

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
