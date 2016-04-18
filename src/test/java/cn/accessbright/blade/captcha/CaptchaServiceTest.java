package cn.accessbright.blade.captcha;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * 同时测试两种验证码实现（CustomCaptchaServiceImpl、KaptchaCaptchaServiceImpl），验证码生成路径默认为d:/captchas<br>
 *
 * 验证方法：运行单元测试。检查输出目录的验证码是否和图片名称一致（不包括后缀名）
 *
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
        String captchaKey = UUID.randomUUID().toString();

        byte[] image = captchaService.generateImage(captchaKey);
        String value = captchaHolder.get(captchaKey);
        System.out.println("==============generating image======================");
        File captcha = new File(outputPath, value + ".jpg");
        OutputStream outputStream = null;

        outputStream = new FileOutputStream(captcha);
        outputStream.write(image);
        outputStream.flush();
        outputStream.close();
        System.out.println("==========="+captcha.toString()+"====image has bean generated=====================");


        System.out.println(Base64Utils.encodeToString(image));
    }

    @Parameterized.Parameters
    public static Collection<AbstractCaptchaService> init() throws Exception {
        KaptchaCaptchaServiceImpl kaptchaCaptchaService = new KaptchaCaptchaServiceImpl();
        kaptchaCaptchaService.afterPropertiesSet();
        CustomCaptchaServiceImpl customCaptchaService = new CustomCaptchaServiceImpl();
        return Arrays.asList(kaptchaCaptchaService, customCaptchaService);
    }


}
