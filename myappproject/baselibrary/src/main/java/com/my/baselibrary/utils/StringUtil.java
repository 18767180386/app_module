package com.my.baselibrary.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AIJU on 2017-04-17.
 */

public class StringUtil {
    public static String EMPTY_STRING = "";
    public static String[] shopArr={"127"};



    /**
     * 不为空 返回真
     *
     * @param str 字符串
     * @return 如果字符串不为空且长度大于1 返回真 ，其他返回假
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().equals(EMPTY_STRING);
    }

    /**
     * 如果为空 返回真
     *
     * @param str 字符串
     * @return 如果为空或长度等于零，返回真，其他返回假
     */
    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 去掉空格不为空 返回真
     *
     * @param str 字符串
     * @return 如果字符串不为空且去掉空格长度大于1 返回真 ，其他返回假
     */
    public static boolean isNotTrimBlank(String str) {
        return str != null && !str.trim().equals(EMPTY_STRING);
    }

    /**
     * 判断密码长度是否大于6小于12
     *
     * @param str
     * @return 如果密码包含空格，返回会假，密码长度不够6位和大于12位返回假，其它返回真
     */
    public static boolean checkPwdLength(String str) {
        if (str.contains(" "))
            return false;
        if (str.length() >= 6 && str.length() <= 12)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断密码是否大于2种
     *
     * @param str
     * @return
     */
    public static boolean checkPwdType(String str) {
        Pattern p2 = Pattern
                .compile("^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&]))[\\dA-Za-z!@#$%^&]+$");
        Matcher m2 = p2.matcher(str);
        if (!m2.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 去掉空格为空返回真
     *
     * @param str 字符串
     * @return 如果字符串为空或去掉空格长度为0, 返回真，其他返回假
     */
    public static boolean isTrimBlank(String str) {
        return str == null || str.trim().equals(EMPTY_STRING);
    }

    /**
     * 首字母大写
     *
     * @param str 要转换的字符串
     * @return 首字母大写的字符串
     */
    @SuppressLint("DefaultLocale")
    public static String capFirstUpperCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }

    /**
     * 首字母小写
     *
     * @param str 要转换的字符串
     * @return 首字母小写的字符串
     */
    @SuppressLint("DefaultLocale")
    public static String capFirstLowerCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 是否是手机字符串
     *
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str) {
        Pattern p = Pattern.compile("^((\\+?86)|((\\+86)))?1\\d{10}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否是身份证号码
     *
     * @param str
     * @return
     */
    public static boolean isIdCardNumber(String str) {
        Pattern p = Pattern
                .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|[X|x|*])$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 确认密码对比
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isConfirmPassword(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 隐藏手机号码中间4位
     *
     * @param phone
     * @return
     */
    public static String hiddenPhoneNum(final String phone) {
        if (isPhoneNumber(phone)) {
            char[] mobile = phone.toCharArray();
            for (int i = 3; i < 7; i++) {
                mobile[i] = '*';
            }
            return String.valueOf(mobile);
        }
        return "";
    }

    /**
     * 获取当前时间戳
     *
     * @return timeStamp yyyy-mm-dd HH:mm:ss
     */
    public static String getTimeStamp() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        StringBuffer buf = new StringBuffer();
        buf.append(calendar.get(Calendar.YEAR));
        buf.append("-");
        buf.append(String.format("%02d", calendar.get(Calendar.MONTH) + 1));
        buf.append("-");
        buf.append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        buf.append(" ");
        buf.append(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
        buf.append(":");
        buf.append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        buf.append(":");
        buf.append(String.format("%02d", calendar.get(Calendar.SECOND)));
        return buf.toString();
    }

    /**
     * 输入的字符串每4位隔开并添加空格
     */
    public static String add4blank(String str) {
        str = str.replace(" ", "");
        int strLength = str.length() / 4;
        String temp = "";
        for (int i = 0; i < strLength; i++) {
            temp += str.substring(i * 4, (i + 1) * 4);
            temp += " ";
        }
        temp += str.substring(strLength * 4);
        return temp;
    }

    /**
     * 手机号码3 4 4格式
     */
    public static String addmobileblank(String str) {
        if (str.replace(" ", "").length() != 11)
            return str;
        String temp = "";
        temp += str.subSequence(0, 3);
        temp += ' ';
        temp += str.substring(3, 7);
        temp += ' ';
        temp += str.substring(7);
        return temp;
    }

    /**
     * 日期格式转换（Str转Str） 20140506转为2014-05-06
     */
    public static String formatDate(String str) {
        if (str == null || str.equals(""))
            return "";
        if (str.replace("-", "").length() != 8)
            return str;
        String temp = "";
        temp += str.subSequence(0, 4);
        temp += '-';
        temp += str.substring(4, 6);
        temp += '-';
        temp += str.substring(6);
        return temp;
    }

    /**
     * 时间格式转换（Str转Str） 123312转为12:33
     */
    public static String formatTime(String str) {
        String temp = "";
        temp += str.subSequence(0, 2);
        temp += ':';
        temp += str.substring(2, 4);
        return temp;
    }

    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将长日期格式的字符串转换为长整型 1970-09-01 12:00:00
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long convert2long(String date) {
        try {
            if (isNotBlank(date)) {
                SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
                return sf.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 将数字串添加","分隔符，Money格式
     *
     * @return
     */
    public static String fromatMoney(CharSequence s) {
        return fromatMoney(s.toString());
    }

    /**
     * 将数字串添加","分隔符，Money格式
     *
     * @return
     */
    public static String fromatMoney(String str) {
        str = str.replace(",", "");
        String secondStr = "";
        String finalStr = "";
        int pointIndex = 0;
        if (str.contains(".")) {
            pointIndex = str.indexOf(".");
        }
        int intLength = 0;
        if (pointIndex == 0) {
            intLength = str.length();
        } else {
            intLength = str.length() - (str.length() - pointIndex);
        }
        int x = (intLength) % 3;
        secondStr = str.substring(x, intLength);
        int index = 0;
        while ((index + 3) <= secondStr.length()) {
            finalStr += ("," + (secondStr.substring(index, index + 3)));
            index += 3;
        }
        finalStr = str.substring(0, x) + finalStr;
        if (pointIndex != 0)
            finalStr = finalStr + str.substring(pointIndex, str.length());
        if (finalStr.startsWith(","))
            finalStr = finalStr.substring(1);
        return finalStr;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(TIME_FORMAT);
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd ");
        }
    };

    /**
     * 以友好的方式显示时间
     *
     * @param mData
     * @return
     */
    public static String friendly_time(String mData) {
        try {
            Date time = new Date(Long.parseLong(mData));
            return getTimeFormatText(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     * @throws java.text.ParseException
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (java.text.ParseException e) {
            return null;
        }
    }


    public static String formatCount(String count) {
        int c = Integer.valueOf(count);
        return formatCount(c);
    }

    public static String formatCount(int count) {
        if (count > 9999) {
            return String.format("%s万", new BigDecimal(count).divide(new BigDecimal(10000)).setScale(1, RoundingMode.HALF_DOWN).toString());
        } else {
            return String.format("%s", count);
        }
    }

    /**
     * 得到字符串的子字符串
     * Get sub string of original string. Don't consider ArrayIndexOutOfBoundException.
     *
     * @param length 从头部开始子字符串的长度
     *               length of substring from the beginning
     * @param str    初始字符串 original string
     */
    public static String getSubString(String str, int length) {
        if (str == null)
            return null;
        int strLength = str.length();
        return str.substring(0, Math.min(length, strLength));
    }

    /**
     * 使用正则表达式对指定字符串格式进行检查
     * Match string by regular expression.
     *
     * @param source         指定字符串
     *                       string to match
     * @param regularPattern 正则表达式
     *                       regular expression
     */
    public static boolean validateStringPattern(String source, String regularPattern) {
        Pattern pattern = Pattern.compile(regularPattern);
        Matcher matcher = pattern.matcher(source);
        return matcher.matches();
    }

    /**
     * 获取到"yyyy-MM-dd"格式的日期字符串
     * Get date string from date numbers by "yyyy-MM-dd" pattern
     *
     * @param year  Year
     * @param month Month
     * @param day   Day
     */
    public static String getDataStr(int year, int month, int day) {
        String date = year + "-";
        if (month >= 1 && month < 10) {
            date += ("0" + month);
        } else {
            date += month;
        }
        date += "-";
        if (day >= 1 && day < 10) {
            date += ("0" + day);
        } else {
            date += day;
        }
        return date;
    }

    /**
     * 将下载速度转化成字符串显示
     * Get download string from download velocity
     *
     * @param kbVelocity 以KB为单位的数字
     *                   download velocity of "KB/s"
     * @return Donwload velocity string
     * 1) KB/s if v < 1MB/s
     * 2) MB/s as default if v > 1MB/s
     */
    public static String getDownloadVelocity(long kbVelocity) {
        if (kbVelocity < 1024)
            return kbVelocity + "KB/s";
        double mbVelocity = (double) ((kbVelocity * 10) / 1024) / 10;
        if (mbVelocity - (int) mbVelocity == 0)
            return (int) mbVelocity + "MB/s";
        return mbVelocity + "MB/s";
    }

    /**
     * 将整形数组转换成字符串数组
     *
     * @param intArray 要转化的Integer数组
     * @return 转化后的字符串数组
     */
    public static String[] getStringArrayFromIntArray(int[] intArray) {
        String[] strArray = new String[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            strArray[i] = String.valueOf(intArray[i]);
        }
        return strArray;
    }

    /**
     * 获取下载文件的扩展名
     *
     * @param downloadUrl 文件下载地址
     */
    public static String getFileSuffix(String downloadUrl) {
        if (!isEmpty(downloadUrl)) {
            int index = downloadUrl.lastIndexOf('.');
            return downloadUrl.substring(index, downloadUrl.length());
        }
        return null;
    }

    public static boolean filterString(String string)
    {

        return true;
    }

    /**
     * 判断字符串是否为空，为空则defaultText,若defaultText 也为空，则返回“ ”
     * @param text
     * @param defaultText
     * @return
     */
    public static String textIsNull(String text,String defaultText)
    {
        if (!isNull(text))
            return text;
        else {
            if (!isNull(defaultText))
                return " ";
            else
                return defaultText;
        }
    }


    public static String setText(String text,String defaultText)
    {
        if(!TextUtils.isEmpty(text))
        {
            return text;
        }else{
            return defaultText;
        }
    }

    public static boolean isNull(String string)
    {
        if (string == null || "".equals(string))
            return true;
        else
            return false;
    }


    public static String deaResult(double end)
    {
        String endstring = "";
        //最后处理末尾为.0的情况
        if (isDouble(end)) {
            endstring = String.valueOf(filterDouble(end));
        }
        else
            endstring = String.valueOf(end);

        //处理由于double问题运算结果类似12.0200000000000002问题
        if (endstring.contains(".")) {
            if (endstring.length() - endstring.indexOf(".") > 3) {
                String temp = endstring.substring(endstring.indexOf(".") + 3, endstring.indexOf(".") + 4);
                int aa = Integer.valueOf(temp);

                int bb = Integer.valueOf(endstring.substring(endstring.indexOf(".") + 2, endstring.indexOf(".") + 3));
                if (end >= 5)
                    endstring = endstring.substring(0, endstring.indexOf(".") + 2) + String.valueOf(bb + 1);
                else {
                    endstring = endstring.substring(0, endstring.indexOf(".") + 3);
                    endstring = String.valueOf(filterDouble(end));
                }

            }
        }
        return endstring;
    }


    /**
     * 让末尾为.0类的double数字转换为整数
     * @param number
     * @return
     */
    public static int filterDouble(double number) {
        int end = 0;
        String temp = String.valueOf(number);
        if (temp.endsWith(".0")) {
            temp = temp.substring(0, temp.length() - 2);
            end = Integer.valueOf(temp);
        }
        return end;
    }


    /**
     * 让末尾为.0类的double数字字符串转换为整数
     * @param number
     * @return
     */
    public int filterDouble(String number) {
        int end = 0;
        String temp = number;
        if (temp.endsWith(".0")) {
            temp = temp.substring(0, temp.length() - 2);
            end = Integer.valueOf(temp);
        }
        return end;
    }

    /**
     * 判断是否是整数
     * @param number
     * @return
     */
    public static boolean isDouble(double number) {
        int end = 0;
        String temp = String.valueOf(number);
        if (temp.endsWith(".0"))
            return true;
        else
            return false;
    }

    /**
     * 判断是否是整数
     * @param number
     * @return
     */
    public boolean isDouble(String number) {
        int end = 0;
        if (number.endsWith(".0"))
            return true;
        else
            return false;
    }
}
