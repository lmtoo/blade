package cn.accessbright.blade.core;

import cn.accessbright.blade.core.utils.collections.MapReduce;
import cn.accessbright.blade.core.utils.collections.ObjectMapper;
import com.icitic.hrms.core.util.PeriodTime;
import com.icitic.hrms.kq.util.XjConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 继承自PeriodTime，并添加了对相同假期类型时间段连接的支持 <br/>
 * 重写了isConnected方法，来判断相应的假期时间段之间是否可以连接起来
 *
 * @note 具体到日和上下午
 *
 * @author Neusoft
 *
 */
public class KqPeriodTime extends PeriodTime {
	private String holidayType;

	public KqPeriodTime(String startDate, String startTime, String endDate, String endTime, String holidayType) {
		super(startDate, startTime, endDate, endTime);
		this.holidayType = holidayType;
	}

	public boolean isConnected(PeriodTime time) {
		boolean isConnected = super.isConnected(time);
		if (isConnected)
			return true;
		if (XjConstants.TANQINJIA.equals(holidayType))
			return false;

		boolean isBefore = isBefore(startDate, startTime, time.getStartDate(), time.getStartTime());

		PeriodTime pre = isBefore ? this : time;
		PeriodTime post = isBefore ? time : this;

		boolean isEndBefore = isBefore(pre.getEndDate(), pre.getEndTime(), post.getStartDate(), post.getStartTime());

		if (isEndBefore) {
			PeriodTime betweenPeroid = getBetweenPeroid(pre.getEndDate(), pre.getEndTime(), post.getStartDate(), post.getStartTime());
			boolean chanjia = (XjConstants.CHANJIA.equals(holidayType) && betweenPeroid.isPeroidInHoliday());
			boolean other = betweenPeroid.isPeroidInHolidayOrWeekends();
			return chanjia || other;
		}
		return false;
	}

	public PeriodTime copy() {
		return new KqPeriodTime(startDate, startTime, endDate, endTime, holidayType);
	}

    /**
     * Desc: 工具类
     * User: wanglj
     * Date: 2006-5-23
     * Time: 20:44:55
     */
    public static class Tools {

        public static final String REF_SELF = "$SELF";

        public static BigDecimal ZERO = new BigDecimal(0);



        private Tools() {
            //cant instantiate
        }

        public static float betweenHours(String hour1, String hour2) {
            try {
                if (hour1 == null || "".equals(hour1) || hour2 == null || "".equals(hour2)) return 0;
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date d1 = fmt.parse(hour1);
                Date d2 = fmt.parse(hour2);
                float dl = 1.0f;
                if (d2.compareTo(d1) > 0) {
                    dl = d2.getTime() - d1.getTime();
                } else {
                    dl = d1.getTime() - d2.getTime();
                }
                float daynum = dl / (60 * 60 * 1000);
                return daynum;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        public static String fillFive(int n) {
            String str = "" + n;
            if (n <= 9) {
                str = "0000" + n;
            } else if (n <= 99) {
                str = "000" + n;
            } else if (n <= 999) {
                str = "00" + n;
            } else if (n <= 9999) {
                str = "0" + n;
            }
            return str;
        }

        /**
         * 数组复制
         * dest orig长度必须一致
         *
         * @param cl   目标类
         * @param dest 目标对象
         * @param orig 初始对象
         */
        public static void copyArrayObject(Class cl, Object[] dest, Object[] orig) {
            try {
                int count = orig.length;
                for (int i = 0; i < count; i++) {
                    if (dest[i] == null) {
                        Object o = cl.newInstance();
                        dest[i] = o;
                    }
                    BeanUtils.copyProperties(dest[i], orig[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 数组复制
         * dest orig长度必须一致
         *
         * @param dest 目标对象
         * @param orig 初始对象
         * @pdOid 5e48bdac-0e1f-40c3-8fca-6ca66e1417fc
         */
        public static void copyArrayObject(String destClassName, Object[] dest,
                                           Object[] orig) {
            try {
                int count = orig.length;
                for (int i = 0; i < count; i++) {

                    if (dest[i] == null) {
                        Class cl = Class.forName(destClassName);
                        Object o = cl.newInstance();
                        dest[i] = o;
                    }
                    BeanUtils.copyProperties(dest[i], orig[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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

        /**
         * 字符串滤空
         *
         * @param str
         * @return
         */
        public static String filterNull(String str) {
            String s = "";
            if (str == null) {
                s = "";
            } else {
                s = str.trim();
            }
            return s;
        }

        /**
         * 字符串滤空返回""
         *
         * @param str
         * @return
         */
        public static String filterNullToStr(String str) {
            if (str == null || str.equals("null")) return "";
            return str.trim();
        }

        public static String[] filterNullToStr(String[] strs) {
            String[] filtered = new String[strs.length];
            for (int i = 0; i < strs.length; i++) {
                filtered[i] = filterNullToStr(strs[i]);
            }
            return filtered;
        }

        public static String filterNullToHtmlStr(String str) {
            String retVal = filterNullToStr(str);
            if (isEmpty(retVal)) return "&nbsp;";
            return retVal;
        }

        public static String filterNullToStr(Object str) {
            if (str == null || str.equals("null")) return "";
            return str.toString();
        }

        public static String getString(String str) {
            str = filterNullToStr(str);
            if (str.indexOf(",") != -1) {
                str = str.replaceAll(",", "</br>");
            }
            return str;
        }

        /**
         * 替换换行符
         *
         * @param str
         * @param separator
         * @return
         */
        public static String replaceBreaklineSeparator(String str, String separator) {
            str = filterNullToStr(str);
            return StringUtils.replace(str, "\n", separator);
        }

        /**
         * 替换换行符
         *
         * @param str
         * @param separator
         * @return
         */
        public static String replaceSymbolSeparator(String str, String separator) {
            str = filterNullToStr(str);
            return StringUtils.replace(str, "、", "、" + separator);
        }


        /**
         * 字符串滤空返回null或者Double
         *
         * @param str
         * @return
         */
        public static Double filterDoubleNull(String str) {
            Double s = null;
            if (str == null || str.equals("")) {
                s = null;
            } else {
                s = Double.valueOf(str);
            }
            return s;
        }

        /**
         * 字符串滤空返回null字符串
         *
         * @param str
         * @return
         */
        public static String filterNullStr(String str) {
            String s = "null";
            if (str == null || str.equals("")) {
                s = "null";
            } else {
                s = str.trim();
            }
            return s;
        }

        /**
         * 将空字符串转换为“0”
         *
         * @param str
         * @return
         */
        public static String filterNullToZero(String str) {
            String s = "";
            if (str == null || "".equals(str) || "null".equals(str)) {
                s = "0";
            } else {
                s = str.trim();
            }
            return s;
        }

        /**
         * 对对象进行虑空操作
         *
         * @param cl  对象类
         * @param obj 需要虑空的对象
         * @return
         */
        public static Object filterNull(Class cl, Object obj) {
            try {
                if (obj == null) {
                    obj = cl.newInstance();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return obj;
        }

        /**
         * 对对象数组进行虑空操作
         *
         * @param cl  需要滤空的对象类
         * @param obj 需要滤空的对象数组
         * @return 若为空，返回一个长度为1的对象数组。
         */
        public static Object[] filterNull(Class cl, Object[] obj) {
            try {
                if (obj == null) {
                    obj = new Object[]{cl.newInstance()};
                    return obj;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return obj;
        }

        /**
         * 得到按指定格式的系统当前时间
         *
         * @param dateFormat 日期格式
         * @return 格式化的日期字符串
         */
        public static String getSysDate(String dateFormat) {
            if (dateFormat == null || "".equals(dateFormat)) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            Calendar date = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            String dateStr = sdf.format(date.getTime());
            return dateStr;
        }

        /**
         * 返回当前系统的季度
         *
         * @return
         */
        public static String getSysQuarter() {
            String year = Tools.getSysDate("yyyy");
            String month = Tools.getSysDate("MM");
            String quarter = "";
            if ("01,02,03".indexOf(month) >= 0) {
                quarter = "-Q1";
            }
            if ("04,05,06".indexOf(month) >= 0) {
                quarter = "-Q2";
            }
            if ("07,08,09".indexOf(month) >= 0) {
                quarter = "-Q3";
            }
            if ("10,11,12".indexOf(month) >= 0) {
                quarter = "-Q4";
            }
            return year + quarter;
        }

        /**
         * 返回当前系统的季度 减一个季度
         *
         * @return
         */
        public static String getSysQuarterMinusYear() {
            String year = Tools.getSysDate("yyyy");
            String month = Tools.getSysDate("MM");
            String quarter = "";
            if ("01,02,03".indexOf(month) >= 0) {
                quarter = "-Q4";
                year = Tools.minusYear1(Tools.getSysDate("yyyy"), 1);
            }
            if ("04,05,06".indexOf(month) >= 0) {
                quarter = "-Q1";
            }
            if ("07,08,09".indexOf(month) >= 0) {
                quarter = "-Q2";
            }
            if ("10,11,12".indexOf(month) >= 0) {
                quarter = "-Q3";
            }
            return year + quarter;
        }

        /**
         * @param dateStr
         * @return
         */
        public static String getWeekDay(String dateStr) {
            final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                    "星期六"};
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            try {
                date = sdfInput.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (dayOfWeek < 0) {
                dayOfWeek = 0;
            }
            return dayNames[dayOfWeek];
        }

        /**
         * 比较2个日期的大小
         *
         * @param String date1, String date2
         * @return 返回int -1则表示小，0相等，1表示大
         */
        public static int compareToDate(String date1, String date2, String dateFormat) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            Date d = null;
            Date e = null;
            try {
                d = format.parse(date1);
                e = format.parse(date2);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return d.compareTo(e);
        }


        /**
         * 将日期字符转换为指定格式日期字符.缺省格式为yyyy-MM-dd
         *
         * @param dateStr    日期
         * @param dateFormat 日期格式
         * @return
         */
        public static String getDateByFormat(String dateStr, String dateFormat) {
            if (dateFormat == null || "".equals(dateFormat)) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            String str = "";
            try {
                if (dateStr != null && !"".equals(dateStr)) {
                    dateStr = dateStr.replaceAll("年", "-");
                    dateStr = dateStr.replaceAll("月", "-");
                    dateStr = dateStr.replaceAll("日", "");
                    dateStr = dateStr.replaceAll("/", "-");
                    java.sql.Date dt = java.sql.Date.valueOf(dateStr);
                    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                    str = sdf.format(dt);
                } else {
                    str = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        /**
         * 日期加天
         *
         * @param date   日期
         * @param daynum 天数
         * @return 日期
         */
        public static String plusDay(String date, long daynum) {
            if (date == null || "".equals(date)) return "";
            java.sql.Date dt = java.sql.Date.valueOf(date);
            long dl = dt.getTime();
            dl = dl + 24 * 60 * 60 * 1000 * daynum;
            java.sql.Date dt2 = new java.sql.Date(dl);
            return dt2.toString();
        }

        public static Date plusDay(Date date, double days) {
            if (date == null) return date;
            long dl = (long) (24 * 60 * 60 * 1000 * days);
            return new Date(date.getTime() + dl);
        }

        /**
         * 日期减天
         *
         * @param date   日期
         * @param daynum 天数
         * @return 日期
         */
        public static String minusDay(String date, long daynum) {
            if (date == null || "".equals(date)) return "";
            java.sql.Date dt = java.sql.Date.valueOf(date);
            long dl = dt.getTime();
            dl = dl - 24 * 60 * 60 * 1000 * daynum;
            java.sql.Date dt2 = new java.sql.Date(dl);
            return dt2.toString();
        }

        /**
         * 计算日期之间的天数
         *
         * @param date1 被减日期
         * @param date2 减的日期
         * @return 天数
         */
        public static long betweenDays(String date1, String date2) {
            if (date1 == null || "".equals(date1) || date2 == null || "".equals(date2)) return 0;
            java.sql.Date dt1 = java.sql.Date.valueOf(date1);
            java.sql.Date dt2 = java.sql.Date.valueOf(date2);
            long dl = dt1.getTime() - dt2.getTime();
            long daynum = dl / (24 * 60 * 60 * 1000);
            return daynum;
        }

        /**
         * 计算六位日期之间的天数
         *
         * @param date1 被减日期
         * @param date2 减的日期
         * @return 天数
         */
        public static int betweenDaysSix(String date1, String date2) {
            if (date1 == null || "".equals(date1) || date2 == null || "".equals(date2)) return 0;
            try {
                date1 = date1.replaceAll("-", "");
                date2 = date2.replaceAll("-", "");
                int d1 = Integer.parseInt(date1);
                int d2 = Integer.parseInt(date2);
                return d2 - d1;
            } catch (Exception e) {
                return 0;
            }

        }

        /**
         * 计算六位日期之间的月数
         *
         * @param date1
         * @param date2
         * @return 月数
         */
        public static int betweenMonths(String date1, String date2) {
            int months = 0;
            try {
                int date1_year = (new Integer(date1.substring(0, 4))).intValue();
                int date2_year = (new Integer(date2.substring(0, 4))).intValue();
                int date1_month = (new Integer(date1.substring(5, 7))).intValue();
                int date2_month = (new Integer(date2.substring(5, 7))).intValue();

                if (date1_month > date2_month)
                    months = (date1_year - date2_year) * 12 +
                            ((new Integer(date1_month - date2_month)).intValue());
                else
                    months = (date1_year - date2_year - 1) * 12 +
                            ((new Integer(date1_month + 12 - date2_month)).intValue());
            } catch (Exception e) {

            }
            return months;

        }

        /**
         * 字符串日期转成Calendar
         *
         * @param sdate
         * @return Calendar
         */
        public static Calendar convertToCalendar(String sdate) {
            Calendar c = Calendar.getInstance();
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dt = df.parse(sdate);
                c.setTime(dt);
            } catch (Exception e) {

            }
            return c;
        }

        /**
         * 替换字符串的内容 查找内容和替换内容中尽量不出现正则表达式的修饰符如:[] . ^ * ? () 等
         *
         * @param str         字符串
         * @param part        查找的内容
         * @param replacement 替换的内容
         * @return
         * @deprecated
         */
        public static String replaceAll(String str, String part, String replacement) {
            if (part == null || replacement == null || str == null)
                return str;
            int pos = 0;
            if (str.length() == part.length()) {
                if (str.equals(part))
                    return replacement;
                else
                    return str;
            }
            while ((pos = str.indexOf(part)) != -1) {
                String tp = str.substring(0, pos);
                tp += replacement;
                tp += str.substring(pos + part.length(), str.length());
                str = tp;
            }
            return str;
        }

        /**
         * 由于in子句中超过1000个表达式出错，先用此方法把in子句拆分成每500一段。
         *
         * @param ids       in子句中要排列的字符串数组
         * @param fieldName in子句中字段的名称
         * @return 拆分拼写好的sql语句
         */
        public static String splitInSql(String[] ids, String fieldName) {
            StringBuffer sqlStr = new StringBuffer();
            if (ids == null || ids.length == 0 || fieldName == null || "".equals(fieldName))
                return null;
            int len = ids.length;
            int num = 1;
            if (len > 500)
                num = (len % 500 == 0) ? len / 500 : (len / 500) + 1;
            int j = 0;
            int i = 1;
            Hashtable hash = new Hashtable();
            for (; i <= num; i++) {
                StringBuffer sb = new StringBuffer();
                String str = "";
                for (; j < i * 500 && j < len; j++) {
                    if (ids[j] == null || "".equals(ids[j]) || hash.containsKey(ids[j])) continue;
                    sb.append("'")
                            .append(ids[j])
                            .append("',");
                    hash.put(ids[j], "");
                }
                str = sb.toString();
                str = str.substring(0, str.length() - 1);
                sqlStr.append("or ").append(fieldName).append(" in (").append(str).append(") ");
            }
            hash.clear();
            hash = null;
            if (sqlStr.length() > 0) {
                sqlStr.delete(0, 2);
                return "(" + sqlStr.toString() + ")";
            } else
                return "(" + fieldName + " in (''))";
        }

        /**
         * string 数组按指定分隔符号 转换为字符串
         *
         * @param strArray 字符串数组
         * @param delim    分隔符
         * @return 指定格式字符串
         */
        public static String StrArray2String(String[] strArray, String delim) {
            if (strArray == null) return "";
            int size = strArray.length;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(strArray[i]).append(delim);
            }
            return sb.toString();
        }

        /**
         * string 按指定分隔符号 转换为字符串数组
         *
         * @param inStrList
         * @param inStrDeli
         * @return
         */
        public static String[] getStringArray(String inStrList, String inStrDeli) {
            String[] strRes = null;
            int iLength = 0;
            int i = 0;
            StringTokenizer strToken = new StringTokenizer(inStrList, inStrDeli);
            iLength = strToken.countTokens();
            strRes = new String[iLength];
            for (i = 0; i < iLength; i++) {
                strRes[i] = strToken.nextToken();
            }
            return strRes;
        }

        public static String[] getStringArray(List list) {
            String[] strRes = null;
            if (list != null && list.size() > 0) {
                strRes = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    strRes[i] = (String) list.get(i);
                }
            }
            return strRes;
        }

        /**
         * 将数字字符串加上逗号
         *
         * @param old_num
         * @return String
         */
        public static String FormatNum(String old_num) {
            double data = 0;
            String str = "0";
            try {
                if (old_num != null && !old_num.equals("")) {
                    data = Double.parseDouble(old_num);
                    NumberFormat formater = NumberFormat.getInstance();
                    str = formater.format(data);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return str;
        }

        /**
         * 检测指标集或指标项的序号是否在国标范围内
         *
         * @param infoId
         * @return true 在国标范围内  false  不在国标范围内
         */
        public static boolean checkInGBScale(int infoId) {
            return infoId < 200;
        }

        /**
         * 检测指标集或指标项的序号是否在系统使用指标范围内
         *
         * @param infoId
         * @return true 在国标范围内  false  不在国标范围内
         */
        public static boolean checkInProgramScale(int infoId) {
            return infoId >= 700;
        }

        /**
         * 过滤xml字符串中的非法字符
         * 将字符转换成LRO。 in the Unicode specification
         *
         * @param s
         * @return
         */
        public static String filterXML(String s) {
            char[] ch = s.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                if (Character.getType(ch[i]) == 15) {
                    ch[i] = '\u0020';
                }
            }
            s = new String(ch);
            return s;
        }

        /**
         * 日期加上月份
         *
         * @param date   6位日期
         * @param months
         * @return result
         */
        public static String plusMonth(String date, int months) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month, 01);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            firstFlight.add(GregorianCalendar.MONTH, months - 1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }


        /**
         * 日期加上月份
         *
         * @param date   8位日期
         * @param months
         * @return result
         */
        public static String plusMonth2(String date, int months) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
            int day = Integer.parseInt(date.substring(date.indexOf("-", 7) + 1, date.indexOf("-", 7) + 3));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month, day);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            firstFlight.add(GregorianCalendar.MONTH, months - 1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期减去月份
         *
         * @param date   6位日期
         * @param months
         * @return result
         */
        public static String minusMonth(String date, int months) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month, 01);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            firstFlight.add(GregorianCalendar.MONTH, -months - 1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期减去月份
         *
         * @param date   8位日期
         * @param months
         * @return result
         */
        public static String minusMonth2(String date, int months) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
            int day = Integer.parseInt(date.substring(date.indexOf("-", 7) + 1, date.indexOf("-", 7) + 3));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month, day);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            firstFlight.add(GregorianCalendar.MONTH, -months - 1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期加上年份
         *
         * @param date   6位日期
         * @param months
         * @return result
         */
        public static String plusYear(String date, int year1) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month - 1, 01);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            firstFlight.add(GregorianCalendar.YEAR, year1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期加上年份
         *
         * @param date   4位日期
         * @param months
         * @return result
         */
        public static String plusYear1(String date, int year1) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date);
            GregorianCalendar firstFlight = new GregorianCalendar(year, 0, 01);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            firstFlight.add(GregorianCalendar.YEAR, year1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期加上年份
         *
         * @param date   8位日期
         * @param months
         * @return result
         */
        public static String plusYear2(String date, int year1) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
            int day = Integer.parseInt(date.substring(date.indexOf("-", 7) + 1, date.indexOf("-", 7) + 3));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month - 1, day);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            firstFlight.add(GregorianCalendar.YEAR, year1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期减去年份
         *
         * @param date   4位日期
         * @param months
         * @return result
         */
        public static String minusYear1(String date, int year1) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date);
            GregorianCalendar firstFlight = new GregorianCalendar(year, 0, 01);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            firstFlight.add(GregorianCalendar.YEAR, -year1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期减去年份
         *
         * @param date   6位日期
         * @param months
         * @return result
         */
        public static String minusYear(String date, int year1) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month - 1, 01);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            firstFlight.add(GregorianCalendar.YEAR, -year1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 日期减去年份
         *
         * @param date   8位日期
         * @param months
         * @return result
         */
        public static String minusYear2(String date, int year1) {
            String result = "";
            if (filterNull(date).equals(""))
                return "";
            int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
            //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
            int day = Integer.parseInt(date.substring(date.indexOf("-", 7) + 1, date.indexOf("-", 7) + 3));
            GregorianCalendar firstFlight = new GregorianCalendar(year, month - 1, day);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            firstFlight.add(GregorianCalendar.YEAR, -year1);
            Date d = firstFlight.getTime();
            result = formatter.format(d);
            return result;
        }

        /**
         * 判断字符串是否为数字
         *
         * @param str
         * @return
         */
        public static boolean isNum(String str) {
            boolean ret = true;
            try {
                Integer t = new Integer(str);
            } catch (NumberFormatException e) {
                ret = false;
            } finally {
                return ret;
            }
        }

        public static String getSysChnDate() {
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            String month = String.valueOf(date.get(Calendar.MONTH) + 1);
            String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
            year = year.replaceAll("0", "○");
            year = year.replaceAll("1", "一");
            year = year.replaceAll("2", "二");
            year = year.replaceAll("3", "三");
            year = year.replaceAll("4", "四");
            year = year.replaceAll("5", "五");
            year = year.replaceAll("6", "六");
            year = year.replaceAll("7", "七");
            year = year.replaceAll("8", "八");
            year = year.replaceAll("9", "九");
            if (month.length() < 2) {
                month = month.replaceAll("1", "一");
                month = month.replaceAll("2", "二");
                month = month.replaceAll("3", "三");
                month = month.replaceAll("4", "四");
                month = month.replaceAll("5", "五");
                month = month.replaceAll("6", "六");
                month = month.replaceAll("7", "七");
                month = month.replaceAll("8", "八");
                month = month.replaceAll("9", "九");
            } else {
                month = month.replaceAll("10", "十");
                month = month.replaceAll("11", "十一");
                month = month.replaceAll("12", "十二");
            }
            if (day.length() < 2) {
                day = day.replaceAll("1", "一");
                day = day.replaceAll("2", "二");
                day = day.replaceAll("3", "三");
                day = day.replaceAll("4", "四");
                day = day.replaceAll("5", "五");
                day = day.replaceAll("6", "六");
                day = day.replaceAll("7", "七");
                day = day.replaceAll("8", "八");
                day = day.replaceAll("9", "九");
            } else {
                day = day.replaceAll("10", "十");
                day = day.replaceAll("11", "十一");
                day = day.replaceAll("12", "十二");
                day = day.replaceAll("13", "十三");
                day = day.replaceAll("14", "十四");
                day = day.replaceAll("15", "十五");
                day = day.replaceAll("16", "十六");
                day = day.replaceAll("17", "十七");
                day = day.replaceAll("18", "十八");
                day = day.replaceAll("19", "十九");
                day = day.replaceAll("20", "二十");
                day = day.replaceAll("21", "二十一");
                day = day.replaceAll("22", "二十二");
                day = day.replaceAll("23", "二十三");
                day = day.replaceAll("24", "二十四");
                day = day.replaceAll("25", "二十五");
                day = day.replaceAll("26", "二十六");
                day = day.replaceAll("27", "二十七");
                day = day.replaceAll("28", "二十八");
                day = day.replaceAll("29", "二十九");
                day = day.replaceAll("30", "三十");
                day = day.replaceAll("31", "三十一");

            }
            String sdate = year + "年" + month + "月" + day + "日";
            return sdate;
        }

        /**
         * 返回当前年份
         *
         * @return String
         */
        public static String getSysYear() {
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            return year;
        }

        public static String str2Html(String str) {
            if ("".equals(str) || str == null) {
                return "&nbsp;";
            } else
                return str;
        }

        public static String getRequestValue(HttpServletRequest request, String key) {
            String ret = request.getParameter(key);
            if (StringUtils.isBlank(ret)) {
                ret = (String) request.getAttribute(key);
            }
            return Tools.filterNullToStr(ret);
        }

        /**
         * @param id 数组{1,2}
         * @return '1','2'
         */
        public static String getInSql(String[] id) {
            StringBuffer sb = new StringBuffer();
            sb.append("'");
            for (int i = 0; i < id.length; i++) {
                String s1 = id[i];
                sb.append(s1);
                sb.append("','");
            }
            sb.delete(sb.length() - 2, sb.length());
            return sb.toString();
        }

        public static String getSelectCode(String name, String codeSet, String realValue) {
            StringBuffer sb = new StringBuffer();
            sb.append("<select name=" + name + " style='width:140'  onchange=\"forChange()\" >");
            List list = SysCacheTool.queryCodeItemBySetId(codeSet);
            sb.append("<option  value=\"\">---请选择---</option>");
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    CodeItemBO cb = (CodeItemBO) list.get(i);
                    if (cb != null) {
                        String sel = "";
                        if (Tools.filterNull(cb.getItemId()).equals(realValue)) {
                            sel = "selected";
                        }
                        sb.append("<option  value=" + Tools.filterNull(cb.getItemId()) + " " + sel + ">" + Tools.filterNull(cb.getItemName()) + "</option>");
                    }
                }
            }
            sb.append("</select>");
            return sb.toString();
        }

        public static String getSelectCode2(String name, String codeSet, String realValue, String val) {
            StringBuffer sb = new StringBuffer();
            sb.append("<select name=" + name + " style='width:140'  onchange=\"forChange()\" >");
            List list = SysCacheTool.queryCodeItemBySetId(codeSet);
            sb.append("<option  value=\"\">---请选择---</option>");
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    CodeItemBO cb = (CodeItemBO) list.get(i);
                    if (cb != null) {
                        String sel = "";
                        if (Tools.filterNull(cb.getItemId()).equals(realValue)) {
                            sel = "selected";
                        }
                        sb.append("<option  value=" + Tools.filterNull(cb.getItemId()) + " " + sel + ">" + Tools.filterNull(cb.getItemName()) + "</option>");
                    }
                }
            }
            sb.append("</select>");
            return sb.toString();
        }

        public static String castMonth(String month) {
            String mon = "";
            if ("01".equals(month)) {
                mon = "一月份";
            } else if ("02".equals(month)) {
                mon = "二月份";
            } else if ("03".equals(month)) {
                mon = "三月份";
            } else if ("04".equals(month)) {
                mon = "四月份";
            } else if ("05".equals(month)) {
                mon = "五月份";
            } else if ("06".equals(month)) {
                mon = "六月份";
            } else if ("07".equals(month)) {
                mon = "七月份";
            } else if ("08".equals(month)) {
                mon = "八月份";
            } else if ("09".equals(month)) {
                mon = "九月份";
            } else if ("10".equals(month)) {
                mon = "十月份";
            } else if ("11".equals(month)) {
                mon = "十一月份";
            } else if ("12".equals(month)) {
                mon = "十二月份";
            }
            return mon;
        }

        /**
         * 深度克隆  TableVO
         *
         * @param table
         * @return
         */
        public static TableVO deepCloneTable(TableVO table) {
            if (table == null) {
                return null;
            }
            TableVO newTable = new TableVO();
            Tools.copyProperties(newTable, table);
            //table.rowData
            RecordVO[] oldRowData = table.getRowData();
            if (oldRowData != null) {
                RecordVO[] newRowData = new RecordVO[oldRowData.length];
                Tools.copyArrayObject(RecordVO.class, newRowData, oldRowData);
                for (int i = 0; i < oldRowData.length; i++) {
                    if (oldRowData[i] != null) {
                        //table.rowData.cell
                        CellVO[] oldCells = oldRowData[i].getCell();
                        if (oldCells != null) {
                            CellVO[] newCells = new CellVO[oldCells.length];
                            Tools.copyArrayObject(CellVO.class, newCells, oldCells);
                            newRowData[i].setCell(newCells);
                        }
                        //table.rowData.defaultcell
                        CellVO[] oldDefaultCells = oldRowData[i].getDefaultcell();
                        if (oldDefaultCells != null) {
                            CellVO[] newDefaultCells = new CellVO[oldDefaultCells.length];
                            Tools.copyArrayObject(CellVO.class, newDefaultCells, oldDefaultCells);
                            newRowData[i].setDefaultcell(newDefaultCells);
                        }
                    } else {
                        newRowData[i] = null;
                    }
                }
                newTable.setRowData(newRowData);//
            }
            //table.header
            CellVO[] oldHeader = table.getHeader();
            if (oldHeader != null) {
                CellVO[] newHeader = new CellVO[oldHeader.length];
                Tools.copyArrayObject(CellVO.class, newHeader, oldHeader);
                newTable.setHeader(newHeader);
            }
            return newTable;
        }

        /**
         * 深度克隆 CellVO[]
         *
         * @param oldCells
         * @return
         */
        public static CellVO[] deepCloneCellVOArray(CellVO[] oldCells) {
            if (oldCells != null) {
                CellVO[] newCells = new CellVO[oldCells.length];
                Tools.copyArrayObject(CellVO.class, newCells, oldCells);
                return newCells;
            }
            return null;
        }

        /**
         * 对可以比较代码项的代码进行比较.如  学历     博士>硕士>本科>专科
         *
         * @param codeItemId1
         * @param codeItemId2
         * @return -1表示 codeItemId1大于 codeItemId2,0表示相等,1表示codeItemId1小于codeItemId2,传空时抛出异常
         */

        public static int compareCodeItem(String codeItemId1, String codeItemId2) {
    //        if (codeItemId1 == null || "".equals(codeItemId1) || codeItemId2 == null || "".equals(codeItemId2)) {
    //            return -2;
    //        }
            CodeItemBO bo1 = SysCacheTool.findCodeItem(null, codeItemId1);
            CodeItemBO bo2 = SysCacheTool.findCodeItem(null, codeItemId2);
    //        if (bo1 == null || bo2 == null) return -2;
            String compare1 = Tools.filterNull(bo1.getItemCompareValue());
            String compare2 = Tools.filterNull(bo2.getItemCompareValue());
    //        if ("".equals(compare1) || "".equals(compare2)) return -2;
            float com1 = Float.parseFloat(compare1);
            float com2 = Float.parseFloat(compare2);
            if (com1 > com2) return -1;
            else if (com1 == com2) return 0;
            else return 1;

        }

        /**
         * @param setId
         * @param codes
         * @return
         */
        public static boolean isAllCodeIn(String setId, String[] codes) {
            if (codes == null || codes.length == 0) return false;
            List items = SysCacheTool.queryCodeItemBySetId(setId);
            return Arrays.asList(codes).containsAll(ListArrayUtil.map(items, new ObjectMapper() {
                public Object map(Object target) {
                    return ((CodeItemBO) target).getItemId();
                }
            }));
        }

        public static boolean isAllWageUnitIn(String[] unitIds) {
            if (unitIds == null || unitIds.length == 0) return false;
            Collection wageUnits = SysCacheTool.findAllWageUnits();

            List wageUnitIds = ListArrayUtil.reduceMap(wageUnits, new MapReduce() {
                public Object map(Object target) {
                    return ((WageUnitBO) target).getUnitId();
                }

                public boolean reduce(Object target) {
                    return "101".equals(((WageUnitBO) target).getSuperId());
                }
            });

            return Arrays.asList(unitIds).containsAll(wageUnitIds);
        }


        public static String fillTwo(int n) {
            String str = "" + n;
            if (n <= 9) {
                str = "0" + n;
            }
            return str;
        }

        public static String fillFour(int n) {
            String str = "" + n;
            if (n <= 9) {
                str = "000" + n;
            } else if (n <= 99) {
                str = "00" + n;
            } else if (n <= 999) {
                str = "0" + n;
            }
            return str;
        }

        /**
         * 对oldTable中的数字类型的字段求和，合计记录放在最后；
         * 注意：如果是分页的话，仅仅汇总当前页。
         *
         * @param oldTable
         * @param clone    是否克隆一个新的table存放原记录和合计
         * @return 带合计的TableVO
         */
        public static TableVO sumTableVO(TableVO oldTable, boolean clone) {
            TableVO newTable = null;
            if (clone) {
                newTable = Tools.deepCloneTable(oldTable);
            } else {
                newTable = oldTable;
            }

            RecordVO[] r = newTable.getRowData();
            if (r != null && r.length > 0) {
                CellVO[] header = newTable.getHeader();
                CellVO[] sumRow = Tools.deepCloneCellVOArray(header);

                for (int i = 0; i < r.length; i++) {
                    Hashtable hash = r[i].cellArray2Hash();

                    for (int j = 0; j < sumRow.length; j++) {
                        //CellVO sumRowCell = sumRow[j];
                        String itemId = sumRow[j].getItemId();
                        if ("A001001".equals(itemId) || "B001005".equals(itemId) || "C001005".equals(itemId)) {
                            sumRow[j].setValue("合计");
                            continue;
                        } else if ("ID".equals(itemId) || "ORGUID".equals(itemId) || "POSTID".equals(itemId) || "SUBID".equals(itemId)) {
                            continue;
                        }
                        InfoItemBO item = SysCacheTool.findInfoItem(null, sumRow[j].getItemId());
                        //不是整型或小数型，不求和
                        if (InfoItemBO.DATA_TYPE_INT.equals(item.getItemDataType()) || InfoItemBO.DATA_TYPE_FLOAT.equals(item.getItemDataType())) {
                            String value = ((CellVO) hash.get(sumRow[j].getItemId())).getValue();
                            sumRow[j].setValue(Arith.add(sumRow[j].getValue(), value));
                        }
                    }
                }
                RecordVO[] newR = new RecordVO[r.length + 1];
                System.arraycopy(r, 0, newR, 0, r.length);
                RecordVO tmpSumRow = new RecordVO();
                tmpSumRow.setCell(sumRow);
                tmpSumRow.setVirtual(true); //虚拟记录
                newR[newR.length - 1] = tmpSumRow;
                newTable.setRowData(newR);
            }

            return newTable;
        }

        /**
         * 两个数组相减，返回objs1存在但objs2中不存在的对象
         *
         * @param objs1
         * @param objs2
         * @return
         */
        public static Object[] sub(Object[] objs1, Object[] objs2) {
            if (objs1 == null) return null;
            if (objs2 == null) return objs1;
            List l = new ArrayList();
            for (int i = 0; i < objs1.length; i++) {
                boolean include = false;
                for (int j = 0; j < objs2.length; j++) {
                    if (objs1[i].equals(objs2[j])) {
                        include = true;
                        break;
                    }
                }
                if (!include) {
                    l.add(objs1[i]);
                }
            }
            return l.toArray();
        }

        /**
         * refer to the java.text.SimpleDateFormat API
         * Symbol   Meaning                 Presentation        Example
         * ------   -------                 ------------        -------
         * G        era designator          (Text)              AD
         * y        year                    (Number)            1996
         * M        month in year           (Text & Number)     July & 07
         * d        day in month            (Number)            10
         * h        hour in am/pm (1~12)    (Number)            12
         * H        hour in day (0~23)      (Number)            0
         * m        minute in hour          (Number)            30
         * s        second in minute        (Number)            55
         * S        millisecond             (Number)            978
         * E        day in week             (Text)              Tuesday
         * D        day in year             (Number)            189
         * F        day of week in month    (Number)            2 (2nd Wed in July)
         * w        week in year            (Number)            27
         * W        week in month           (Number)            2
         * a        am/pm marker            (Text)              PM
         * k        hour in day (1~24)      (Number)            24
         * K        hour in am/pm (0~11)    (Number)            0
         * z        time zone               (Text)              Pacific Standard Time
         * '        escape for text         (Delimiter)
         * ''       single quote            (Literal)           '
         * Example:
         * Format Pattern                         Result
         * --------------                         -------
         * "yyyy.MM.dd G 'at' hh:mm:ss z"    ->>  1996.07.10 AD at 15:08:56 PDT
         * "EEE, MMM d, ''yy"                ->>  Wed, July 10, '96
         * "h:mm a"                          ->>  12:08 PM
         * "hh 'o''clock' a, zzzz"           ->>  12 o'clock PM, Pacific Daylight Time
         * "K:mm a, z"                       ->>  0:00 PM, PST
         * "yyyyy.MMMMM.dd GGG hh:mm aaa"    ->>  1996.July.10 AD 12:08 PM
         */
        public static String formatDate(Date dt, String dateFormat) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.format(dt);
        }

        public static java.sql.Date toSQLDate(String strDate, String pattern) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date d = formatter.parse(strDate, new ParsePosition(0));
            java.sql.Date sd = new java.sql.Date(d.getTime());
            return sd;
        }

        public static java.sql.Timestamp toTimestamp(String strDate, String pattern) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date d = formatter.parse(strDate, new ParsePosition(0));
            java.sql.Timestamp ts = new java.sql.Timestamp(d.getTime());
            return ts;
        }


        public static boolean isQuarter(String quarter) {
            if (isEmpty(quarter)) return false;
            return quarter.matches("^20\\d{2}-Q\\d$");
        }

        public static String stringDotFilter(String[] strs) {
            if (strs == null) return StringUtils.EMPTY;
            Set kcSet = new HashSet();
            for (int i = 0; i < strs.length; i++) {
                if (!isEmpty(strs[i])) {
                    kcSet.addAll(Arrays.asList(strs[i].split("[,;]")));
                }
            }
            return StringUtils.join(kcSet.iterator(), ";");
        }


        /**
         * @param form
         * @return 返回绝对路径的文件名
         * @throws HrmsException
         */
        public static String uploadFile(ServletContext context, FormFile file) throws HrmsException {
            OutputStream streamOut = null;
            InputStream streamIn = null;
            try {
                // 上传文件
                String dir = context.getRealPath("/file/tmp");// 得到文件目录的路径
                streamIn = file.getInputStream();
                Calendar cld = Calendar.getInstance();
                String fileName = cld.getTimeInMillis() + ".cvf";
                streamOut = new FileOutputStream(dir + File.separator + fileName);// 设置写入文件的路径

                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
                    // System.out.println("读入字节====="+buffer);
                    streamOut.write(buffer);
                }
                streamOut.flush();
                return dir + File.separator + fileName;
            } catch (Exception e) {
                e.printStackTrace(); // To change body of catch statement use File |
                // Settings | File Templates.
                throw new HrmsException("上传文件失败", e, Tools.class);
            } finally {
                try {
                    if (streamOut != null)
                        streamOut.close();
                    if (streamIn != null)
                        streamIn.close();
                    if (file != null)
                        file.destroy();
                } catch (Exception e) {
                    e.printStackTrace(); // To change body of catch statement use
                    // File | Settings | File Templates.
                }
            }
        }

        /**
         * 2014年11月4日10:50:55
         * lgd
         *
         * @param form
         * @return 返回绝对路径的文件名
         * @throws HrmsException
         */
        public static String uploadTxtFile(HttpServlet servlet, FormFile file, String id) throws HrmsException {
            OutputStream streamOut = null;
            InputStream streamIn = null;
            try {
                // 上传文件
                String dir = servlet.getServletContext().getRealPath("/file/tmp");// 得到文件目录的路径
                streamIn = file.getInputStream();
                String fileName = id + ".txt";
                streamOut = new FileOutputStream(dir + File.separator + fileName);// 设置写入文件的路径
                int bytesRead;
                byte[] buffer = new byte[file.getFileSize()];
                while ((bytesRead = streamIn.read(buffer, 0, file.getFileSize())) != -1) {
                    // System.out.println("读入字节====="+buffer);
                    streamOut.write(buffer);
                }
                streamOut.flush();
                return dir + File.separator + fileName;
            } catch (Exception e) {
                e.printStackTrace(); // To change body of catch statement use File |
                // Settings | File Templates.
                throw new HrmsException("上传文件失败", e, Tools.class);
            } finally {
                try {
                    if (streamOut != null)
                        streamOut.close();
                    if (streamIn != null)
                        streamIn.close();
                    if (file != null)
                        file.destroy();
                } catch (Exception e) {
                    e.printStackTrace(); // To change body of catch statement use
                    // File | Settings | File Templates.
                }
            }
        }

        /**
         * 获取map的值，并忽略大小写
         *
         * @param target
         * @param property
         * @param ignoreKeyCase
         * @return
         */
        private static Object getPropValue(Map target, String property, boolean ignoreKeyCase) {
            if (target.containsKey(property)) {
                return target.get(property);
            }
            if (ignoreKeyCase) {
                if (target.containsKey(property.toLowerCase())) {
                    return target.get(property.toLowerCase());
                } else if (target.containsKey(property.toUpperCase())) {
                    return target.get(property.toUpperCase());
                }
            }
            return null;
        }

        /**
         * 设置map的值，并忽略大小写
         *
         * @param target
         * @param property
         * @param value
         * @param ignoreKeyCase
         */
        private static void setPropValue(Map target, String property, Object value, boolean ignoreKeyCase) {
            if (ignoreKeyCase) {
                if (target.containsKey(property.toLowerCase())) {
                    target.put(property.toLowerCase(), value);
                    return;
                } else if (target.containsKey(property.toUpperCase())) {
                    target.put(property.toUpperCase(), value);
                    return;
                }
            }
            target.put(property, value);
            return;
        }

        /**
         * 获取对象或者map的属性，并忽略map的key的大小写
         *
         * @param target
         * @param property
         * @return
         */
        public static Object getPropValue(Object target, String property) {
            if (target != null) {
                if (target instanceof Map) {
                    return getPropValue((Map) target, property, true);
                } else {
                    try {
                        return PropertyUtils.getProperty(target, property);
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }


        /**
         * 设置对象或者map的属性，并忽略map的key的大小写
         *
         * @param target
         * @param property
         * @param value
         */
        public static void setPropValue(Object target, String property, Object value) {
            if (target != null) {
                if (target instanceof Map) {
                    setPropValue((Map) target, property, value, true);
                } else {
                    try {
                        PropertyUtils.setProperty(target, property, value);
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        public static Map getPropNameMapper(Class clazz, boolean isKeyUppercase) {
            Map mapper = new HashMap();
            PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(clazz);

            for (int i = 0; i < propDescs.length; i++) {
                PropertyDescriptor desc = propDescs[i];
                String name = desc.getName();
                String key = isKeyUppercase ? StringUtils.upperCase(name) : StringUtils.lowerCase(name);
                mapper.put(key, name);
            }

            return mapper;
        }


        public static FormatDescriptions getFormatDescriptions(Class clazz) {
            return ViewConfigPropertiesHolder.getInstance().getViewDescriptions(clazz);
        }

        public static String[] getPropText(Object target, String[] props) {
            String[] values = new String[props.length];
            for (int i = 0; i < props.length; i++) {
                values[i] = getPropText(target, props[i]);
            }
            return values;
        }

        public static String getPropText(Object target, String property) {
            if (target == null) return "";
            Object value = getPropValue(target, property);
            return filterNullToStr(value);
        }

        public static String format(Object target, String pattern) {
            if (target == null) return "";
            return TextFormatter.format(pattern, target);
        }

        public static String getPropText(Object target, String property, String codeType) {
            String code = getPropText(target, property);
            return filterNullToStr(SysCacheTool.interpertCode(codeType, code));
        }

        public static String formatMonth(String year, int month) {
            return year + "-" + formatMonth(month);
        }

        public static String formatMonth(int month) {
            return (month < 10 ? "0" : "") + month;
        }

        public static String[] allFormatMonth() {
            String[] months = new String[12];
            for (int i = 0; i < 12; i++) {
                months[i] = formatMonth(i + 1);
            }
            return months;
        }

        public static BigDecimal toNumber(String num) {
            return new BigDecimal(filterNullToZero(num));
        }

        public static BigDecimal toNumber(Object num) {
            return new BigDecimal(filterNullToZero(num + ""));
        }


        public static BigDecimal toNumber(double num) {
            return new BigDecimal(num);
        }

        public static String toNumberString(String num) {
            return toNumber(num).toString();
        }

        public static String toNumberString(double num, int scale) {
            return toNumber(num).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        public static String toNumberString(String num, int scale) {
            return toNumber(num).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }


    }
}