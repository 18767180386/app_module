package com.my.baselibrary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AIJU on 2017-06-05.
 */

public class TimeUtils {
    private static final long MAXMILLS = 5 * 1000;
    private static long backMills = 0;

    /**
     * 获取天数
     *
     * @param mills
     * @return
     */
    public static int getDay(long mills) {
        int day = -1;
        if (mills > 0) {
            day = (int) (mills / 1000 / 60 / 60 / 24);
        }
        return day;
    }

    /**
     * 获取24制的小时数
     *
     * @param mills
     * @return
     */
    public static int getHour(long mills) {
        int hour = -1;
        if (mills > 0) {
            hour = (int) (mills / 1000 / 60 / 60) - getDay(mills) * 24;
        }
        return hour;
    }

    /**
     * 获取分数
     *
     * @param mills
     * @return
     */
    public static int getMinutes(long mills) {
        int min = -1;
        if (mills > 0) {
            min = (int) (mills / 1000 / 60) - getDay(mills) * 24 * 60 - getHour(mills) * 60;
        }
        return min;
    }

    /**
     * 获取毫秒数
     * @param days
     * @return
     */
    public static long getMills(int days)
    {
        long mills = -1;
        if (days >= 0)
        {
            mills = days * 24 * 60 * 60 * 1000;
        }
        return mills;
    }

    public static long getMillsByHour(int hours)
    {
        long mills = -1;
        if (hours >= 0)
        {
            mills = hours * 60 * 60 * 1000;
        }
        return mills;
    }

    public static String dateFormatYYYYmmdd(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String dateFormatYYYYmm(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    public static String dateFormatHHmmss(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public static String dateFormatYYYYMMDDHHMM(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    public static String dateFormatYYYYMMDDHH(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        return format.format(date);
    }

    public static String dateFormatAll(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date stringFormatDate(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        // Fri Feb 24 00:00:00 CST 2012
        date = format.parse(string);

        return date;
    }

    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static final String StringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象
        Date d = null ;
        try{
            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace() ;       // 打印异常信息
        }
        return sdf2.format(d);
    }

    public final static boolean compare(String time1, String time2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (diff > 0)
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return true;
        }
    }



    public static boolean Back() {
        long time = System.currentTimeMillis() - backMills;
        if (time > MAXMILLS) {
            backMills = System.currentTimeMillis();
            return true;
        } else {
            backMills = System.currentTimeMillis();
            return false;
        }
    }



    private static   String getCurDate(int day)
    {
        Date d=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        return  df.format(new Date(d.getTime() - (long)day * 24 * 60 * 60 * 1000));
    }


}
