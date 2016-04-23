package cn.accessbright.blade.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lile_ on 2016/4/23.
 */
public class Dates {
    public static Date plusDay(Date date, double days) {
        if (date == null) return date;
        long dl = (long) (24 * 60 * 60 * 1000 * days);
        return new Date(date.getTime() + dl);
    }

    public static String toString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 得到按指定格式的系统当前时间
     *
     * @param format
     * @return
     */
    public static String current(String format) {
        return toString(new Date(), format);
    }

    public static String currentDateTime() {
        return current("yyyy-MM-dd HH:mm:ss");
    }

    public static String currentDate() {
        return current("yyyy-MM-dd");
    }

    public static String currentTime() {
        return current("HH:mm:ss");
    }

    public static String currentYear() {
        return current("yyyy");
    }

    public static String currentMonth() {
        return current("MM");
    }

    public static String currentQuarter() {
        String month = currentMonth();
        if ("01,02,03".indexOf(month) >= 0) {
            return "1";
        }
        if ("04,05,06".indexOf(month) >= 0) {
            return "2";
        }
        if ("07,08,09".indexOf(month) >= 0) {
            return "3";
        }
        if ("10,11,12".indexOf(month) >= 0) {
            return "4";
        }
        return "0";
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
     * 日期加上月份
     *
     * @param date   6位日期
     * @param months
     * @return result
     */
    public static String plusMonth(String date, int months) {

        String result = "";
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
        if (Strings.isEmpty(date))
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
     * @param date1, date2
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
}
