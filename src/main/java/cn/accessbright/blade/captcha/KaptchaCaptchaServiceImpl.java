package cn.accessbright.blade.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * Created by Administrator on 2016/4/13.
 */
@Component("kaptchaCaptchaService")
public class KaptchaCaptchaServiceImpl extends AbstractCaptchaService implements InitializingBean {
    private DefaultKaptcha producer;

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties config = new Properties();
        config.setProperty("kaptcha.textproducer.char.length", String.valueOf(textLength));
        config.setProperty("kaptcha.textproducer.char.string", textChars);
        config.setProperty("kaptcha.image.width", String.valueOf(width));
        config.setProperty("kaptcha.image.height", String.valueOf(height));
        config.setProperty("kaptcha.textproducer.font.size",String.valueOf(fontSize));
        config.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        config.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");

        producer = new DefaultKaptcha();
        producer.setConfig(new Config(config));
    }

    @Override
    protected String generateCaptchaText() {
        return producer.createText();
    }

    @Override
    protected BufferedImage createImage(String text) {
        return producer.createImage(text);
    }
}
