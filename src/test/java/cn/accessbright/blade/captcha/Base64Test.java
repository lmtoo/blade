package cn.accessbright.blade.captcha;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/15.
 */
public class Base64Test {

    private File outputPath = new File("d:/captchas");


    @Test
    public void testBase64Decode() throws IOException {

        byte[] image = Base64.decodeBase64("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");

        FileOutputStream outputStream = new FileOutputStream(new File(outputPath, "1.jpg"));
        outputStream.write(image);
        outputStream.flush();
        outputStream.close();

    }

}