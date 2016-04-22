package cn.accessbright.blade.core.utils;

/**
 * Created by Administrator on 2016/4/19.
 */
public class Regexs {

    //身份证验证正则表达式
    public static final String ID_CARD_PATTERN = "^\\d{6}(\\d{4})([0,1]\\d)([0-3]\\d)\\d{2}(\\d)[0-9xX]$";

    //日期验证正则表达式
    public static final String DATE_PATTERN = "^\\s*\\d{4}-\\d{2}-\\d{2}\\s*$";



}
