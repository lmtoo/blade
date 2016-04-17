package cn.accessbright.blade.captcha;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/13.
 */
@Component("customCaptchaService")
public class CustomCaptchaServiceImpl extends AbstractCaptchaService {
    private static Logger logger = Logger.getLogger(CustomCaptchaServiceImpl.class);

    private Random random = new Random();
    private int lineSize = 20;

    @Override
    protected String generateCaptchaText() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= this.textLength; ++i) {
            builder.append(this.getCharAt(this.random.nextInt(this.textChars.length())));
        }
        return builder.toString();
    }

    @Override
    protected BufferedImage createImage(String text) {
        BufferedImage image = new BufferedImage(this.width, this.height, 4);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, this.width, this.height);
        g.setFont(new Font("Times New Roman", 0, fontSize));
        g.setColor(this.getRandColor(110, 133));

        for (int randomString = 0; randomString <= this.lineSize; ++randomString) {
            this.drowLine(g);
        }

        for (int i = 1; i <= text.length(); ++i) {
            g.setFont(new Font("Fixedsys", 1, fontSize));
            g.setColor(new Color(this.random.nextInt(101), this.random.nextInt(111), this.random.nextInt(121)));
            String rand = String.valueOf(text.charAt(i - 1));
            g.drawString(rand, 13 * i, 18);
            g.translate(this.random.nextInt(3), this.random.nextInt(3));
        }
        g.dispose();
        return image;
    }


    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + this.random.nextInt(bc - fc - 16);
        int g = fc + this.random.nextInt(bc - fc - 14);
        int b = fc + this.random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }


    private void drowLine(Graphics g) {
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        int xl = this.random.nextInt(13);
        int yl = this.random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }


    private char getCharAt(int index) {
        return this.textChars.charAt(index);
    }
}
