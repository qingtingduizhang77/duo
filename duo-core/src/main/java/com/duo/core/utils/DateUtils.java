package com.duo.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author yonsin
 * @version 2018-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /** 一秒有多少毫秒 **/
    public static final long RATE_SECOND = 1000;
    /** 一分钟有多少毫秒 **/
    public static final long RATE_MINUTE = 60 * RATE_SECOND;
    /** 一小时有多少毫秒 **/
    public static final long RATE_HOUR = 60 * RATE_MINUTE;
    /** 一天有多少毫秒 **/
    public static final long RATE_DAY = 24 * RATE_HOUR;

    /**
     * 一小时的秒数
     */
    private static final int HOUR_SECOND = 60 * 60;

    /**
     * 一分钟的秒数
     */
    private static final int MINUTE_SECOND = 60;

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date addMillisecond(Date date, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    public static Date setYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date setMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date setDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date setMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date setSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date setMillisecond(Date date, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 转换为标准的字符串, 如 2012-08-08 20:00:00.000
     *
     * @param date 待处理的日期
     * @return 日期字符串
     */
    public static String toNormativeString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(date);
    }

    /**
     * 转换为日期字符串, 如 2012-08-08
     *
     * @param date 待处理的日期
     * @return 日期字符串
     */
    public static String toDateString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 转换为时间字符串, 如 20:00:00.000
     *
     * @param date 待处理的日期
     * @return 时间字符串
     */
    public static String toTimeString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 转换为日期加时间字符串, 如 2012-08-08 20:00:00
     *
     * @param date 待处理的日期
     * @return 日期字符串
     */
    public static String toDateTimeString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 转换为第1时间<br>
     * Calendar.YEAR=当年第1时间, Calendar.MONTH=当月第1时间, Calendar.DAY_OF_MONTH=当日第1时间, ...<br>
     * 如 toFirstTime(2016-08-08 20:30:40.500, Calendar.YEAR) --- 2016-01-01 00:00:00.000<br>
     * 如 toFirstTime(2016-08-08 20:30:40.500, Calendar.MONTH) --- 2016-08-01 00:00:00.000<br>
     * 如 toFirstTime(2016-08-08 20:30:40.500, Calendar.DAY_OF_MONTH) --- 2016-08-08 00:00:00.000<br>
     * 如 toFirstTime(2016-08-08 20:30:40.500, Calendar.HOUR_OF_DAY) --- 2016-08-08 20:00:00.000<br>
     * 如 toFirstTime(2016-08-08 20:30:40.500, Calendar.MINUTE) --- 2016-08-08 20:30:00.000<br>
     * 如 toFirstTime(2016-08-08 20:30:40.500, Calendar.SECOND) --- 2016-08-08 20:30:40.000<br>
     *
     * @param date 待处理的日期
     * @param field 类型
     * @return 第1时间
     */
    public static Date toFirstTime(Date date, int field) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case Calendar.YEAR:
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
            case Calendar.MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            case Calendar.DAY_OF_MONTH:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR_OF_DAY:
                calendar.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                calendar.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTime();
    }

    /**
     * 转换为最后时间<br>
     * Calendar.YEAR=当年最后时间, Calendar.MONTH=当月最后时间, Calendar.DAY_OF_MONTH=当日最后时间, ...<br>
     * 如 toLastTime(2016-08-08 20:30:40.500, Calendar.YEAR) --- 2016-12-31 23:59:59.999<br>
     * 如 toLastTime(2016-08-08 20:30:40.500, Calendar.MONTH) --- 2016-08-31 23:59:59.999<br>
     * 如 toLastTime(2016-08-08 20:30:40.500, Calendar.DAY_OF_MONTH) --- 2016-08-08 23:59:59.999<br>
     * 如 toLastTime(2016-08-08 20:30:40.500, Calendar.HOUR_OF_DAY) --- 2016-08-08 20:59:59.999<br>
     * 如 toLastTime(2016-08-08 20:30:40.500, Calendar.MINUTE) --- 2016-08-08 20:30:59.999<br>
     * 如 toLastTime(2016-08-08 20:30:40.500, Calendar.SECOND) --- 2016-08-08 20:30:40.999<br>
     *
     * @param date 待处理的日期
     * @param field 类型
     * @return 最后时间
     */
    public static Date toLastTime(Date date, int field) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case Calendar.YEAR:
                calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            case Calendar.MONTH:
                // 下月1日的前一天
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            case Calendar.DAY_OF_MONTH:
                calendar.set(Calendar.HOUR_OF_DAY, 23);
            case Calendar.HOUR_OF_DAY:
                calendar.set(Calendar.MINUTE, 59);
            case Calendar.MINUTE:
                calendar.set(Calendar.SECOND, 59);
            case Calendar.SECOND:
                calendar.set(Calendar.MILLISECOND, 999);
        }
        return calendar.getTime();
    }

    /**
     * 转换为结束时间, 即设置时分秒为00:00:00
     *
     * @param date 待处理的日期
     * @return 结束时间
     */
    public static Date toStartTime(Date date) {
        if (date == null) {
            return null;
        }
        return toFirstTime(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 转换为结束时间, 即设置时分秒为23:59:59
     *
     * @param date 待处理的日期
     * @return 结束时间
     */
    public static Date toEndTime(Date date) {
        if (date == null) {
            return null;
        }
        return toLastTime(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当天的毫秒数
     *
     * @param date
     * @return
     */
    public static long getMillisOfDay(Date date) {
        Date start = toStartTime(date);
        return date.getTime() - start.getTime();
    }
    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        if(date==null) return null;
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 根据秒数获取时间串
     *
     * @param second (eg: 100s)
     * @return (eg : 00 : 01 : 40)
     */
    public static String getTimeStrBySecond(long second) {

        long day = second / 60 / 60 / 24;
        long hour = second / 60 / 60 % 24;
        long minute = second / 60 % 60;
        long seconds = second % 60;

        StringBuilder sb = new StringBuilder();
        if (day != 0) {
            sb.append(day).append("天");
        }
        if (hour != 0) {
            sb.append(hour).append("小时");
        }
        if (minute != 0) {
            sb.append(minute).append("分");
        }
        if (seconds != 0) {
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }


    /**
     * 获取两个日期之间的秒数
     *
     * @param before
     * @param after
     * @return
     */
    public static String getSecondOfTwoDate(Date before, Date after,String defaultValue) {
        if(before==null) return defaultValue;
        if(after==null) after=new Date();
        long beforeTime = before.getTime();
        long afterTime = after.getTime();

        return secondToTime((afterTime - beforeTime) / (1000 ));
    }
    /**
     * 将秒数转换为日时分秒，
     * @param second
     * @return
     */
    public static String secondToTime(long second){
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        if(days>0){
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        }else if(hours>0){
            return hours + "小时" + minutes + "分" + second + "秒";
        }else if(minutes>0){
            return  minutes + "分" + second + "秒";
        }else{
            return  second + "秒";
        }
    }

    /**
     * @param args
     * @throws ParseException
     */
//	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//	}


    public static final long SECOND = 1000;

    public static final long MINUTE = 60 * SECOND;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final long WEEK = 7 * DAY;

    public static final String SHORTFORMAT = "yyyy-MM-dd";

    public static final String SHORTFORMATNOSPIT = "yyyyMMdd";

    public static final String YEARMONTHFORMAT = "yyyy-MM";

    public static final String LONGFORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 获得yymmdd类型的日期
     * @param date
     * @return
     */
    public static String getDateString(Date date){
        String year =(date.getYear()+1900)+"";
        String mm = (date.getMonth()+1)+"";
        if(Integer.valueOf(mm).intValue()<10){
            mm="0"+mm;
        }
        String day = date.getDate()+"";
        if(Integer.valueOf(day).intValue()<10){
            day="0"+day;
        }
        return year+mm+day;
    }
    /**
     * 获得yy-mm-dd类型的日期
     * @param date
     * @return
     */
    public  static String DateString(Date date){
        String year =(date.getYear()+1900)+"";
        String mm = (date.getMonth()+1)+"";
        if(Integer.valueOf(mm).intValue()<10){
            mm="0"+mm;
        }
        String day = date.getDate()+"";
        if(day.length()==1){
            day="0"+day;
        }
        return year+"-"+mm+"-"+day;
    }


    // 得到某一天是星期几
    public static int getDateInWeek(String strDate) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(strDate);
            java.util.Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            return day;
        } catch (ParseException e) {
            return -1;
        }
    }


    //得到当前日期

    public static String getCurrentDate() {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd");
        java.util.Date nowdate = new java.util.Date();
        String str_date = d.format(nowdate);
        return str_date;
    }

    //得到当前日期时间
    public static String getCurrentDateTime() {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd HH:mm:ss");
        java.util.Date nowdate = new java.util.Date();
        String str_date = d.format(nowdate);
        return str_date;

    }
    //根据日期获得日期时间
    public static String getDateTime(java.util.Date date) {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd HH:mm:ss");
        String str_date = d.format(date);
        return str_date;
    }
    //获得某月的最后一天
    public static int getDayNum(int year, int month) {
        if (month == 2) {
            return year % 400 != 0 && (year % 4 != 0 || year % 100 == 0) ? 28
                    : 29;
        }
        String SmallMonth = ",4,6,9,11,";
        return SmallMonth.indexOf(String.valueOf(String
                .valueOf((new StringBuffer(",")).append(String.valueOf(month))
                        .append(",")))) < 0 ? 31 : 30;
    }

    //返回两个日期之间隔了多少天

    public static int DateDiff(Date date1, Date date2) {
        int i = (int) ( (date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
        return i;
    }


    //从放有日期的字符串中得到，相应的年，月，日 style is "Y"or"y" 返回年 style is "M"or"m" 返回月 style
    //is "D"or"d" 返回日 日期字符串要求 "YYYY-MM-DD"
    public static int getYearMonthDate(String strDate, String style) {
        int year;
        int month;
        int day;
        int firstDash;
        int secondDash;
        if (strDate == null) {
            return 0;
        }
        firstDash = strDate.indexOf('-');
        secondDash = strDate.indexOf('-', firstDash + 1);
        if ((firstDash > 0) & (secondDash > 0)
                & (secondDash < strDate.length() - 1)) {
            year = Integer.parseInt(strDate.substring(0, firstDash));
            month = Integer.parseInt(strDate.substring(firstDash + 1,
                    secondDash));
            day = Integer.parseInt(strDate.substring(secondDash + 1));
        } else {
            return 0;
        }
        if (style.equalsIgnoreCase("Y")) {
            return year;
        } else if (style.equalsIgnoreCase("M")) {
            return month;
        } else if (style.equalsIgnoreCase("D")) {
            return day;
        } else {
            return 0;
        }
    }
    // 某一天，过几天后是几号

    public static java.sql.Date DateAdd(java.sql.Date date, int addday) {
        java.sql.Date datenew = null;
        int year = getYearMonthDate(date.toString(), "Y");
        int month = getYearMonthDate(date.toString(), "M");
        int day = getYearMonthDate(date.toString(), "D");
        day = day + addday;
        String dayStr = Integer.toString(year) + "-" + Integer.toString(month) +
                "-" + Integer.toString(day);
        datenew = datenew.valueOf(dayStr);
        //datenew.setTime(datenew.getTime()+day*3600*24*1000);
        //有问题。 改
        return datenew;

    }

    //某一天的前几天是几号

    public static java.sql.Date DateBefore(java.sql.Date date, int addday) {
        java.sql.Date datenew = null;
        int year = getYearMonthDate(date.toString(), "Y");
        int month = getYearMonthDate(date.toString(), "M");
        int day = getYearMonthDate(date.toString(), "D");
        day = day - addday;
        String dayStr = Integer.toString(year) + "-" + Integer.toString(month)
                + "-" + Integer.toString(day);
        datenew = datenew.valueOf(dayStr);
        // datenew.setTime(datenew.getTime()+day*3600*24*1000);
        // 有问题。 改
        return datenew;
    }



    //某一天是否是年的头一天

    public static boolean isYearFirstDay(java.sql.Date date) {
        boolean i = false;
        if ((getYearMonthDate(date.toString(), "M") == 1)
                && (getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }

    // 某一天是否是半年的头一天

    public static boolean isHalfYearFirstDay(java.sql.Date date) {
        boolean i = false;
        if (((getYearMonthDate(date.toString(), "M") == 1) && ( getYearMonthDate(date.toString(), "D") == 1))
                || ((getYearMonthDate(date.toString(), "M") == 7) && ( getYearMonthDate(date.toString(), "D") == 1))) {
            i = true;
        }
        return i;
    }

    public static String getHalfYearFirstDay(java.sql.Date date) {
        String month = "01";
        if (getYearMonthDate(date.toString(), "M") >= 7) {
            month = "07";
        }
        String day = Integer.toString(getYearMonthDate(date
                .toString(), "Y"))
                + "-" + month + "-01";
        return day;
    }

    // 某一天是否是半年的最后一天

    public static boolean isHalfYearLastDay(java.sql.Date date) {
        boolean i = false;
        if (((getYearMonthDate(date.toString(), "M") == 12) && ( getYearMonthDate(date.toString(), "D") == 31))
                || ((getYearMonthDate(date.toString(), "M") == 6) && ( getYearMonthDate(date.toString(), "D") == 30))) {
            i = true;
        }
        return i;
    }

    public static String getHalfYearLastDay(java.sql.Date date) {
        String month = "-06-30";
        if (getYearMonthDate(date.toString(), "M") >= 7) {
            month = "-12-31";
        }
        String day = Integer.toString(getYearMonthDate(date.toString(), "Y"))
                + "-" + month;
        return day;
    }

    // 某一天是否是年的最后一天

    public static boolean isYearLastDay(java.sql.Date date) {
        boolean i = false;
        if ((getYearMonthDate(date.toString(), "M") == 12)
                && (getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        return i;
    }

    //某一天是否是季的头一天


    public static boolean isQuarterFirstDay(java.sql.Date date) {
        boolean i = false;
        if (((getYearMonthDate(date.toString(), "M") == 1)
                || (getYearMonthDate(date.toString(), "M") == 4)
                || (getYearMonthDate(date.toString(), "M") == 7) || ( getYearMonthDate(date.toString(), "M") == 10))
                && (getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }

    public static String getQuarterFirstDay(java.sql.Date date) {
        String month = "01";
        if (getYearMonthDate(date.toString(), "M") >= 10) {
            month = "10";
        } else if (getYearMonthDate(date.toString(), "M") >= 7) {
            month = "07";
        } else if (getYearMonthDate(date.toString(), "M") >= 4) {
            month = "04";
        } else if (getYearMonthDate(date.toString(), "M") >= 1) {
            month = "01";
        }

        String day = Integer.toString(getYearMonthDate(date
                .toString(), "Y"))
                + "-" + month + "-01";
        return day;
    }

    //某一天是否是季的最后一天

    public static boolean isQuarterLastDay(java.sql.Date date) {
        boolean i = false;
        if ((getYearMonthDate(date.toString(), "M") == 3)
                && (getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        if ((getYearMonthDate(date.toString(), "M") == 6)
                && (getYearMonthDate(date.toString(), "D") == 30)) {
            i = true;
        }
        if ((getYearMonthDate(date.toString(), "M") == 9)
                && (getYearMonthDate(date.toString(), "D") == 30)) {
            i = true;
        }
        if ((getYearMonthDate(date.toString(), "M") == 12)
                && (getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        return i;
    }

    public static String getQuarterLastDay(java.sql.Date date) {
        String month = "-01-31";
        if (getYearMonthDate(date.toString(), "M") >= 10) {
            month = "-12-31";
        } else if (getYearMonthDate(date.toString(), "M") >= 7) {
            month = "-09-30";
        } else if (getYearMonthDate(date.toString(), "M") >= 4) {
            month = "-06-30";
        }

        String day = Integer.toString(getYearMonthDate(date
                .toString(), "Y"))
                + "-" + month;
        return day;
    }
    //某一天是否是月的最后一天


    public static boolean isMonthLastDay(java.sql.Date date) {
        boolean i = false;
        java.sql.Date des_date = null;
        String month;
        String str_date = date.toString();
        String year = str_date.substring(0, str_date.indexOf("-"));
        int m = new Integer(str_date.substring(str_date.indexOf("-") + 1,
                str_date.lastIndexOf("-"))).intValue() + 1;
        month = new Integer(m).toString();
        if (m < 10) {
            month = "0" + month;
        }
        java.sql.Date mid_date = null;
        mid_date = mid_date.valueOf(year + "-" + month + "-01");
        des_date = DateAdd(mid_date, -1);
        if (DateDiff(des_date, date) == 0) {
            i = true;
        }
        return i;
    }

    //某一天是否是月的第一天


    public static boolean isMonthFisrtDay(java.sql.Date date) {
        boolean i = false;
        if ((getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }
    //获得月的第一天
    public static String getMonthFisrtDay(java.sql.Date date)

    {
        String month;
        if ( getYearMonthDate(date.toString(), "M") > 9) {
            month = Integer.toString(getYearMonthDate(date
                    .toString(), "M"));
        } else {
            month = "0"
                    + Integer.toString(getYearMonthDate(date
                    .toString(), "M"));
        }
        String day = Integer.toString(getYearMonthDate(date
                .toString(), "Y"))
                + "-" + month + "-01";
        return day;
    }

    public static java.sql.Timestamp getTimestamp() {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            String mystrdate = myFormat.format(calendar.getTime());
            return java.sql.Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Timestamp getTimestamp(String datestr) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String mystrdate = myFormat.format(myFormat.parse(datestr));
            return java.sql.Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }
    //格式化日期（Y-年，M-月,D-日 HH:mm:ss 小时：分钟：秒）
    public static String getDate(java.util.Date date, String format) {
        String result = null;
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(format);
            result = myFormat.format(date);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static String getDate(java.util.Date date) {
        return getDate(date, LONGFORMAT);
    }
    //将日期格式化成yyyy-MM-dd形式
    public static java.util.Date format(String datestr) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = myFormat.parse(datestr);
            return date;
        } catch (Exception e) {
            return new Date(datestr);
        }
    }
    //格式化日期（Y-年，M-月,D-日 HH:mm:ss 小时：分钟：秒）
    public static java.util.Date format(String datestr, String format) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(format);
            Date date = myFormat.parse(datestr);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    //获得数据库格式的日期
    public static java.sql.Date getSqlDate(java.util.Date date) {
        java.sql.Date result = null;
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            String mystrdate = myFormat.format(date);
            result = java.sql.Date.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static java.util.Date addMonth2(java.util.Date date, int month) {
        String strdate = getDate(date, SHORTFORMAT);
        int curmonth = Integer.parseInt(strdate.substring(5, 7));
        int year = Integer.parseInt(strdate.substring(0, 4));
        int addyear = month / 12;
        year = year + addyear;
        curmonth = curmonth + (month % 12);
        if (curmonth > 12) {
            curmonth = 1;
            year = year + 1;
        }
        String strmonth = String.valueOf(curmonth);
        if (strmonth.length() == 1) {
            strmonth = "0" + strmonth;
        }
        strdate = String.valueOf(year) + "-" + strmonth + "-"
                + strdate.substring(8, 10);
        return format(strdate, SHORTFORMAT);
    }

    /**
     * 传递日期，  获得上个月的最后1天
     * @param dt
     * @return
     */
    public static String getUpMDate(Date dt){
        dt.setDate(1);
        dt.setDate(dt.getDate()-1);
        return dt.toLocaleString();
    }

    /**
     * 将10位日期格式化为8位
     * @param dt
     * @return
     */
    public static String getShortDate(String dt){
        if(dt != null){
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = myFormat.parse(dt);
                return getDate(date,SHORTFORMATNOSPIT);
            } catch (ParseException e) {
                return dt;
            }
        }else
            return dt;
    }

    /**
     * 将8位日期格式化为10位
     * @param dt
     * @return
     */
    public static String getLongDate(String dt){
        if(dt != null){
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = myFormat.parse(dt);
                return getDate(date,SHORTFORMAT);
            } catch (ParseException e) {
                return dt;
            }
        }else
            return dt;
    }

    /**
     * 判断是否是当月
     * @param date
     * @return
     */
    public static boolean isSameYearMonth(String date){
        try{
            String currdate = getCurrentDate();
            currdate = getShortDate(currdate).substring(0,6);
            String lastdate = getShortDate(date).substring(0,6);
            if(lastdate.equals(currdate)){
                return true;
            }else{
                return false;
            }
        }catch(NumberFormatException e){
            return false;
        }
    }


    public static void main(String[] args){
//        Date d = new Date("20070829");
//        Date f = new Date("20070912");
        String str = DateUtils.getDateString(new Date()) + "c88f4490002d484d8eb98cbaecfb58ec";
        System.out.println(str);
    }

}
