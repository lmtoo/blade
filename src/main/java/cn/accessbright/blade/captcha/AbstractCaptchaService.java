package cn.accessbright.blade.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 抽象的验证码服务，子类需实现生成验证码文本，和根据文本生成验证码图片的逻辑，<br>
 * <p>
 * 提供了验证码图片长宽、字体大小、随机文本的设置<br>
 * <p>
 * 验证码服务，需要依赖验证码缓存
 * <p>
 * Created by Administrator on 2016/4/13.
 */
public abstract class AbstractCaptchaService implements CaptchaService {

    @Autowired
    @Qualifier("sessionCaptchaHolder")
    protected CaptchaHolder captchaHolder;

    protected int width = 80;
    protected int height = 26;
    protected int fontSize = 24;

    protected int textLength = 4;
    protected String textChars = "0123456789";// "0123456789abcdefghijklmnopqrstuvwxyz";

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public void setTextChars(String textChars) {
        this.textChars = textChars;
    }

    public void setCaptchaHolder(CaptchaHolder captchaHolder) {
        this.captchaHolder = captchaHolder;
    }

    protected abstract String generateCaptchaText();

    @Override
    public boolean validate(String captchaKey, String captchaValue) {
        String text = captchaHolder.get(captchaKey);
        if (text == null) {
            throw new CaptchaException("captcha Key " + captchaKey + " not found!");
        }

        if (text.equals(captchaValue)) {
            captchaHolder.remove(captchaKey);
            return true;
        }
        return false;
    }

    @Override
    public byte[] generateImage(String captchaKey) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeImageTo(captchaKey, out);
        return out.toByteArray();
    }

    @Override
    public void writeImageTo(String captchaKey, OutputStream output) {
        String text = generateCaptchaText();
        captchaHolder.put(captchaKey, text);

        if (text == null) {
            throw new CaptchaException("captcha Key " + captchaKey + " not found!");
        }

        BufferedImage image = createImage(text);
        try {
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            throw new CaptchaException("Failed to write captcha stream!", e);
        }
    }

    protected abstract BufferedImage createImage(String text);
}
