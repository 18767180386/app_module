package com.my.baselibrary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import org.apache.commons.lang3.time.DateUtils;
/**
 * Created by AIJU on 2017-04-17.
 */

public class DateUtils {
    /**
     * 比较日期大小
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算date之前n天的日期
     */
    public static Date getDateBefore(Date date, int n) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - n);
        return now.getTime();
    }

    /**
     * 计算date之前n天的日期
     */
    public static Date getDateBefore(int n) {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        Calendar now = Calendar.getInstance();
        now.setTime(curDate);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - n);
        return now.getTime();
    }

    /**
     * 计算date之前n天的日期--->String
     */
    public static String getDateBeforeToString(String dateString, int n) {
        String str = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = formatter.parse(dateString);
            Date dateBefore = getDateBefore(parse, n);
            str = formatter.format(dateBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 计算date之前n天的日期--->String
     */
    public static String getDateBeforeToString(int n) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        Date dateBefore = getDateBefore(curDate, n);
        String str = formatter.format(dateBefore);
        return str;
    }

    /**
     * 计算当前的日期--->String(ymd)
     */
    public static String getCurrentDateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
    /**
     * 计算当前的日期--->String(ymdhms)
     */
    public static String getCurrentDateToString2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 得到几天后的时间
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

//    /**
//     * 获取本月第一天的日期
//     */
//    public static String getBenYueFirstDate() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal_1 = Calendar.getContext();//获取当前日期
//        cal_1.add(Calendar.MONTH, -1);
//        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
//        String firstDate = format.format(cal_1.getTime());
//        return firstDate;
//    }
//
//    /**
//     * 获取本月最后一天的日期
//     */
//    public static String getBenYueLastDate() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cale = Calendar.getContext();
//        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为0号,当前日期既为本月最后一天
//        String lastDay = format.format(cale.getTime());
//        return lastDay;
//    }

    /**
     * 获取本周第一天和最后一天的日期
     */
    public static Map getWeekDay() {
        Map<String, String> map = new HashMap<String, String>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        map.put("mon", df.format(cal.getTime()));
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        map.put("sun", df.format(cal.getTime()));
        return map;
    }

//    /**
//     * 获取本月第一天和最后一天的日期
//     */
//    public static Map getMonthkDay() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("month_first", getBenYueFirstDate());
//        map.put("month_last", getBenYueLastDate());
//        return map;
//    }

    /**
     * 获取本月第一天和最后一天的日期
     */
    public static Map getMonthDate() {
        Map<String, String> map = new HashMap<String, String>();
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 设置时间,当前时间不用设置
        // calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        map.put("monthF", format.format(calendar.getTime()));
        print("*********得到本月的最小日期**********" + format.format(calendar.getTime()));
        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        // 打印
        map.put("monthL", format.format(calendar.getTime()));
        print("*********得到本月的最大日期**********" + format.format(calendar.getTime()));
        return map;
    }

    private static void print(Object o) {
        System.out.println(o.toString());
    }

    /**
     * 前7天的日期
     */

    public static String getAllNear7Days() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }


    /**
     * 计算某天与今天相差的天数
     */

    public static int daysBetween(String dateStr) throws ParseException {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date compareDate = sdf.parse(dateStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(compareDate);
        long time1 = cal.getTimeInMillis();

        cal.setTime(today);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算某天与某天相差的天数
     */

    public static int daysBetween(String startStr, String endStr) throws ParseException {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date compareDate = sdf.parse(endStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(compareDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(today);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 取当前时间的前几天的日期
     */

    public static String getStatetime(int aa) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -aa);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     *字符串的日期格式的计算
     */
    public static int daysBetweenString(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     *获得距今天几个月之前的那天
     */
    public static String getMonthBeforeDateString(int  beforeMonth) throws ParseException {
        Date dNow = new Date(); //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.MONTH, -beforeMonth); //设置为前3月
        dBefore = calendar.getTime(); //得到前3月的时间

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        return defaultStartDate;
    }



}
