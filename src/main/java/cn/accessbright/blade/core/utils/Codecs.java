package cn.accessbright.blade.core.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by lile_ on 2016/4/24.
 */
public class Codecs {


    /**
     * 将字符串进行MD5加密
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        BigInteger bi = new BigInteger(digest);
        return bi.toString(16);
    }


}
