package com.chisondo.server.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_TIME_PATTERN2 = "yyyyMMddHHmmss";

	public final static String DATE_TIME_PATTERN3 = "yyyy-MM-dd HH:mm";

	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return currentDateStr();
    }

    public static String currentDateStr() {
	    return currentDateStr(DATE_PATTERN);
    }
    public static String currentDateStr(String pattern) {
	    return format(new Date(), pattern);
    }

    public static Date currentDate() {
	    return new Date();
    }

    public static Date parseDate(String dateStr) {
	    String pattern = DATE_PATTERN;
	    if (dateStr.length() == 16) {
	        pattern = "yyyy-MM-dd HH:mm";
        } else if (dateStr.length() == 19) {
	        pattern = DATE_TIME_PATTERN;
        }
	    return parseDate(dateStr, pattern);
    }
    public static Date parseDate(String dateStr, String pattern) {

        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {

            throw new RuntimeException("date format error");
        }
    }

    /**
     * 两个日期之间的间隔分钟
     * @param sDate
     * @param eDate
     * @return
     */
    public static Integer getBetweenMinutes(Date sDate,Date eDate) {
        long minute = 60L * 1000L;
        Integer betweenMinutes = new Integer((int) ((eDate.getTime() - sDate.getTime()) / minute));
        return Math.abs(betweenMinutes);
    }
    /**
     * 两个日期之间的间隔小时
     * @param sDate
     * @param eDate
     * @return
     */
    public static Integer getBetweenHours(Date sDate,Date eDate) {
        long hour = 60L * 60L * 1000L;
        Integer betweenHours = new Integer((int) ((eDate.getTime() - sDate.getTime()) / hour));
        return Math.abs(betweenHours);
    }
    /**
     * 两个日期之间的间隔天数
     * @param sDate
     * @param eDate
     * @return
     */
    public static Integer getBetweenDays(Date sDate,Date eDate) {
        long DAY = 24L * 60L * 60L * 1000L;
        return new Integer((int)((eDate.getTime() - sDate.getTime())/DAY));
    }
    /**
     * 两个日期之间的间隔月数
     * @param sDate
     * @param eDate
     * @return
     */
    public static Integer getBetweenMonths(Date sDate,Date eDate) {
        int betweentYears = getBetweenYears(sDate,eDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sDate);
        int month1 = calendar.get(Calendar.MONTH);
        calendar.setTime(eDate);
        int month2 = calendar.get(Calendar.MONTH);
        return new Integer(betweentYears*12 +( month2 - month1));
    }
    /**
     * 两个日期之间间隔的年数
     * @param sDate
     * @param eDate
     * @return
     */
    public static Integer getBetweenYears(Date sDate,Date eDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sDate);
        int year1 = calendar.get(Calendar.YEAR);
        calendar.setTime(eDate);
        int year2 = calendar.get(Calendar.YEAR);
        return new Integer(year2 - year1);
    }

    public static Date addDateHour(Object date,Object hour){
        double h = Double.valueOf(hour.toString());
        return addDateMinute(date,(int)(h * 60));
    }

    public static Date addDateMinute(Object date,int min){
        Calendar c = Calendar.getInstance();
        c.setTime((Date)date);
        c.add(Calendar.MINUTE, min);
        return c.getTime();
    }

    public static String getWeek(Date date) {
        // 再转换为时间
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static int getWeekNumber(Date date){
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        return cd.get(Calendar.DAY_OF_WEEK) - 1;
    }

}
