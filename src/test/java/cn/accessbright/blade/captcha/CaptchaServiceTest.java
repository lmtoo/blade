package cn.accessbright.blade.captcha;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Administrator on 2016/4/13.
 */
@RunWith(Parameterized.class)
public class CaptchaServiceTest {

    private CaptchaService captchaService;

    private CaptchaHolder captchaHolder;

    private File outputPath = new File("d:/captchas");


    public CaptchaServiceTest(AbstractCaptchaService captchaService) {
        captchaHolder = new HashMapCaptchaHolder();
        captchaService.setCaptchaHolder(captchaHolder);
        this.captchaService = captchaService;
        if (!outputPath.exists()) {
            outputPath.mkdirs();
        }
    }

    @Test
    public void testGeneateImage() throws IOException {
        String captchaKey = captchaService.generateKey();

        byte[] image = captchaService.generateImage(captchaKey);
        String value = captchaHolder.get(captchaKey);
        System.out.println("==============generating image======================");
        File captcha = new File(outputPath, value + ".jpg");
        OutputStream outputStream = null;

        outputStream = new FileOutputStream(captcha);
        outputStream.write(image);
        outputStream.flush();
        outputStream.close();

        System.out.println("===============image has bean generated=====================");
    }

    @Parameterized.Parameters
    public static Collection<AbstractCaptchaService> init() throws Exception {
        KaptchaCaptchaServiceImpl kaptchaCaptchaService = new KaptchaCaptchaServiceImpl();
        kaptchaCaptchaService.afterPropertiesSet();
        CustomCaptchaServiceImpl customCaptchaService = new CustomCaptchaServiceImpl();
        return Arrays.asList(kaptchaCaptchaService, customCaptchaService);
    }
}
