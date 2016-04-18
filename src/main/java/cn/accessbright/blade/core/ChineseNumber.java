package cn.accessbright.blade.core;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseNumber {
	private static Pattern CHN_NUM_PATTERN = Pattern.compile("[一二三四五六七八九][十百千]?");
	private static Map CHN_UNITS = new HashMap();
	private static Map CHN_NUMS = new HashMap();
	static {
		CHN_UNITS.put(new Character('十'), BigInteger.valueOf(10));
		CHN_UNITS.put(new Character('百'), BigInteger.valueOf(100));
		CHN_UNITS.put(new Character('千'), BigInteger.valueOf(1000));
		CHN_UNITS.put(new Character('万'), BigInteger.valueOf(10000));
		CHN_UNITS.put(new Character('亿'), BigInteger.valueOf(10000000));
		CHN_NUMS.put(new Character('一'), BigInteger.valueOf(1));
		CHN_NUMS.put(new Character('二'), BigInteger.valueOf(2));
		CHN_NUMS.put(new Character('三'), BigInteger.valueOf(3));
		CHN_NUMS.put(new Character('四'), BigInteger.valueOf(4));
		CHN_NUMS.put(new Character('五'), BigInteger.valueOf(5));
		CHN_NUMS.put(new Character('六'), BigInteger.valueOf(6));
		CHN_NUMS.put(new Character('七'), BigInteger.valueOf(7));
		CHN_NUMS.put(new Character('八'), BigInteger.valueOf(8));
		CHN_NUMS.put(new Character('九'), BigInteger.valueOf(9));
	}

	/**
	 * 将小于一万的汉字数字， 转换为BigInteger
	 *
	 * @param chnNum
	 * @return
	 */
	private static BigInteger getNumber(String chnNum) {
		BigInteger number = BigInteger.valueOf(0);
		Matcher m = CHN_NUM_PATTERN.matcher(chnNum);
		m.reset(chnNum);
		while (m.find()) {
			String subNumber = m.group();
			if (subNumber.length() == 1) {
				number = number.add((BigInteger) CHN_NUMS.get(new Character(subNumber.charAt(0))));
			} else if (subNumber.length() == 2) {
				number = number.add((BigInteger) CHN_NUMS.get(new Character(subNumber.charAt(0)))).multiply(
						(BigInteger) CHN_UNITS.get(new Character(subNumber.charAt(1))));
			}
		}
		return number;
	}

	/**
	 * 将汉字转换为数字
	 *
	 * @param chnNum
	 * @return
	 */
	public static int parseNumber(String chnNum) {
		chnNum = chnNum.replaceAll("(?<![一二三四五六七八九])十", "一十").replaceAll("零", "");
		Pattern pattern = Pattern.compile("[万亿]");
		Matcher m = pattern.matcher(chnNum);
		BigInteger result = BigInteger.valueOf(0);
		int index = 0;
		while (m.find()) {
			int end = m.end();
			BigInteger multiple = (BigInteger) CHN_UNITS.get(new Character(m.group().charAt(0)));
			String num = chnNum.substring(index, m.start());
			result = result.add(getNumber(num)).multiply(multiple);
			index = end;
		}
		String num = chnNum.substring(index);
		result = result.add(getNumber(num));
		return result.intValue();
	}
}