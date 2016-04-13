package cn.accessbright.blade.captcha;

import java.io.IOException;

/**
 * Created by Administrator on 2016/4/13.
 */
public class CaptchaException extends RuntimeException {

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String string, IOException e) {
        super(string, e);
    }

}
