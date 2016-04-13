package cn.accessbright.blade.captcha;

import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class AbstractCaptchaService implements CaptchaService {

    @Autowired
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

    @Override
    public String generateKey() {
        String key = UUID.randomUUID().toString().replaceAll("-","");
        String value = generateCaptchaText();
        captchaHolder.put(key, value);
        return key;
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
        String text = captchaHolder.get(captchaKey);

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