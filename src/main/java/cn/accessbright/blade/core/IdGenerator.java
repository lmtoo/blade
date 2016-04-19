package cn.accessbright.blade.core;

import java.util.Random;
import java.util.UUID;

/**
 * id生成器
 *
 * @author Neusoft
 */
public class IdGenerator {
    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String randomChars() {
        return randomChars(4);
    }

    public static String randomChars(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(range.charAt(random.nextInt(range.length())));
        }
        return result.toString();
    }
}
