package com.my.baselibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.my.baselibrary.base.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by AIJU on 2017-04-17.
 */

public class Util {
    private final String TAG = Util.class.getName();

    public static final int PAGESIZE =20;

    public static final int INDEXPAGESIZE =10;
    private final static String PREFS_DEVICE_ID = "uu_device_id";
    private final static String SAVE_KEY_FILE = "dsbconfigfile";

    /**
     * 判断是否是第一次出现
     */
    public final static String FIRST_LOAD = "FIRST_LOAD";
    public final static String PLAY_COUNT = "PLAY_COUNT";
    public final static String IS_NEW_VERSION = "IS_NEW_VERSION";
    public final static String IS_FIRST_START = "IS_FIRST_START";
    public final static String IS_FIRST_START_AD = "IS_FIRST_START_AD";
    public final static String AD_DATA = "AD_DATA";
    public static void Log(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void putPreferenceString(Context context, String key,
                                           String values) {
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, values);
        editor.commit();
    }

    public static String getPreferenceString(Context context, String key) {
        if (context == null)
            return "";
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        return preference.getString(key, "");
    }

    public static void putPreferenceBoolean(Context context, String key,
                                            boolean values) {
        SharedPreferences preference = context.getSharedPreferences(SAVE_KEY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    public static boolean getPreferenceBoolean(Context context, String key,
                                               boolean defaultValues) {
        if (context == null)
            return false;
        SharedPreferences preference = context.getSharedPreferences(SAVE_KEY_FILE, Context.MODE_PRIVATE);
        return preference.getBoolean(key, defaultValues);
    }

    public static void putPreferenceFloat(Context context, String key,
                                          float values) {
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putFloat(key, values);
        editor.commit();
    }

    public static float getPreferenceFloat(Context context, String key,
                                           float defaultValues) {
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        return preference.getFloat(key, defaultValues);
    }

    public static void putPreferenceInt(Context context, String key, int values) {
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(key, values);
        editor.commit();
    }

    public static int getPreferenceInt(Context context, String key,
                                       int defaultValues) {
        if (context == null)
            return 0;
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        return preference.getInt(key, defaultValues);
    }

    public static void putPreferenceLong(Context context, String key,
                                         long values) {
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putLong(key, values);
        editor.commit();
    }

    public static long getPreferenceLong(Context context, String key,
                                         long defaultValues) {
        SharedPreferences preference = context.getSharedPreferences(
                SAVE_KEY_FILE, Context.MODE_PRIVATE);
        return preference.getLong(key, defaultValues);
    }

    /**
     * 获取imei码
     *
     * @param context
     * @return
     */
    public static String getImeiCode(Context context) {
        return getDeviceUUId(context);
    }

    /**
     * 获取version
     * @param context
     * @return
     */
    public static String getCurrenVersionName(Context context) {
        String versionName = "1.0";
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  versionName;
    }

    /**
     * 获取meta值
     *
     * @param context
     * @param channel
     *            umeng渠道的键
     * @param def
     *            默认渠道号
     * @return
     */
    public static String getMetaDataValue(Context context, String channel,
                                          String def) {

        String value = getMetaDataValue(context, channel);

        return (value == null) ? def : value;

    }

    public static String getMetaDataValue(Context context, String name) {
        Object value = null;
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (value == null) {
            throw new RuntimeException("The name '" + name
                    + "' is not defined in the manifest file's meta data.");
        }
        return value.toString();
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        if (context != null) {

            PackageManager manager = context.getPackageManager();
            PackageInfo info;
            try {
                info = manager.getPackageInfo(context.getPackageName(), 0);
                versionCode = info.versionCode; // 版本名
                String versionName = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return versionCode;
    }


    public static String getAppVersionName(Context context) {
        String versionName = "";
        if (context != null) {

            PackageManager manager = context.getPackageManager();
            PackageInfo info;
            try {
                info = manager.getPackageInfo(context.getPackageName(), 0);
                versionName = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return versionName;
    }

    public static String getImeiNumber(Context context) {
        return getDeviceUUId(context);
    }

    public static String getMacAddresses(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String macAdd = "";
        try {
            WifiInfo info = wifi.getConnectionInfo();
            macAdd = info.getMacAddress();
        } catch (Exception e) {
            macAdd = "";
        }
        return macAdd;// MAC 地址
    }

    /**
     * 获取设备的唯一id
     * @return
     */
    public static String getDeviceUUId(Context context) {
        String deviceUUId = "";
        try {
            deviceUUId = SharedPreferencesUtil.getDeviceUUIDByKey(
                    PREFS_DEVICE_ID, "");
            if (!TextUtils.isEmpty(deviceUUId)) {
                // 使用原来已存储的uu id
                return deviceUUId;
            } else {
                // 使用imei
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(context.TELEPHONY_SERVICE);
                deviceUUId = tm.getDeviceId();
                if (!TextUtils.isEmpty(deviceUUId)) {
                    SharedPreferencesUtil.setDeviceUUIDByKey(PREFS_DEVICE_ID,
                            deviceUUId);
                    return deviceUUId;
                }

                // 使用 cup 序列号
                deviceUUId = getCpuSerial();
                if (!"0000000000000000".equalsIgnoreCase(deviceUUId)) {
                    // cup 序列号 是有效的
                    SharedPreferencesUtil.setDeviceUUIDByKey(PREFS_DEVICE_ID,
                            deviceUUId);
                    return deviceUUId;
                }

                // 使用 Android ID
                deviceUUId = Settings.Secure.getString(BaseApplication.getInstance().getContext()
                        .getContentResolver(), Settings.Secure.ANDROID_ID);
                if (!"9774d56d682e549c".equals(deviceUUId)) {
                    // 安卓id 号 是有效的
                    SharedPreferencesUtil.setDeviceUUIDByKey(PREFS_DEVICE_ID,
                            deviceUUId);
                    return deviceUUId;
                }
                // 使用一个随机的UUID
                deviceUUId = UUID.randomUUID().toString();
                SharedPreferencesUtil.setDeviceUUIDByKey(PREFS_DEVICE_ID,
                        deviceUUId);
                return deviceUUId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "00000000";
        }
    }

    /**
     *   获取cpu的序列号
     * @return cpu serial
     */
    public static String getCpuSerial() {
        String cpuSerial = "";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null && str.startsWith("Serial")) {
                    cpuSerial = str.substring(str.indexOf(":")+1, str.length()).trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            Log.i("my tag", "获取IP信息错误");
        }
        return cpuSerial;
    }


    /**
     *
     *获取app信息
     * @param context
     * @return
     */
    public static String getAppInfo(Context context) {
        String pkName=null;
        try {
            pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            // return pkName + "   " + versionName + "  " + versionCode;
            return pkName;
        } catch (Exception e) {
        }
        return pkName;
    }




    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String toHexString(byte[] b) {
        // String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            if(TextUtils.isEmpty(s)) {
                return "";
            }
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取图片宽 保持宽在700以下
     *
     * @param srcPath
     * @return
     */
    public static void compressImage(String srcPath, String desPath) {

        try {

            // ==================测量
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float ww =480f; //600f;
            float hh = 800f;//1000f;
            // 缩放比。
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            bitmap = BitmapRotateUtil.getInstance().checkBitmapAngleToAdjust(
                    srcPath, bitmap);
            // ==================压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 压缩到小于800kb,
            while (baos.toByteArray().length / 1024 > 800) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            // File myCaptureFile = new File( + fileName + ".jpg");
            BufferedOutputStream fbos;
            try {
                fbos = new BufferedOutputStream(new FileOutputStream(desPath));
                fbos.write(baos.toByteArray());
                // fbos = new BufferedOutputStream(baos);
                fbos.flush();
                fbos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }



    /**
     * 获取图片宽 保持宽在700以下
     *
     * @param srcPath
     * @return
     */
    public static void compressImageNew(String srcPath, String desPath,int comsize) {

        try {

            // ==================测量
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float ww =480f; //600f;
            float hh = 800f;//1000f;
            // 缩放比。
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            bitmap = BitmapRotateUtil.getInstance().checkBitmapAngleToAdjust(
                    srcPath, bitmap);
            // ==================压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            // 压缩到小于800kb,
            while (baos.toByteArray().length / 1024 > comsize) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            // File myCaptureFile = new File( + fileName + ".jpg");
            BufferedOutputStream fbos;
            try {
                fbos = new BufferedOutputStream(new FileOutputStream(desPath));
                fbos.write(baos.toByteArray());
                // fbos = new BufferedOutputStream(baos);
                fbos.flush();
                fbos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }


    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = BaseApplication.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String moneyFormat(double money) {
        if (money == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(money);
    }

    /**
     * 计算距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static String calculateDistance(String lat1, String lng1,
                                           String lat2, String lng2) {
        double pk = (double) (180 / Math.PI);
        try {
            double a1 = Double.parseDouble(lat1) / pk;
            double a2 = Double.parseDouble(lng1) / pk;
            double b1 = Double.parseDouble(lat2) / pk;
            double b2 = Double.parseDouble(lng2) / pk;
            double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1)
                    * Math.cos(b2);
            double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1)
                    * Math.sin(b2);
            double t3 = Math.sin(a1) * Math.sin(b1);
            double tt = Math.acos(t1 + t2 + t3);

            int md = (int) (6366000 * tt / 10);
            double m = ((double) md) / 100;
            if (m == 0) {
                m = 0.01;
            }
            return m + "km";
        } catch (Exception e) {
            return "未知";
        }
    }

    public static String parseDistance(String distance) {
        try {
            int md = (int) (Double.valueOf(distance) / 10);
            double m = ((double) md) / 100f;
            if (m >= 2) {
                DecimalFormat df = new DecimalFormat("0.0");
                return df.format(m) + "km";
            } else {
                if (m == 0) {
                    m = 0.01;
                }
                m *= 1000;
                return (int) m + "米以内";
            }
        } catch (Exception e) {

        }
        return "";
    }


    public static String parseDistances(String distance) {
        try {
            int md = (int) (Double.valueOf(distance) / 10);
            double m = ((double) md) / 100f;
            if (m >= 2) {
                DecimalFormat df = new DecimalFormat("0.0");
                return df.format(m) + "km";
            } else {
                if (m == 0) {
                    m = 0.01;
                }
                m *= 1000;
                return (int) m + "m";
            }
        } catch (Exception e) {

        }
        return "";
    }


    /**
     * 从assets里取数据
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context a, String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(a
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result;
        }
    }


    /**
     * 将输入流保存成文件
     *
     * @param ins
     * @param file
     */
    public static void inputstreamtoFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得系统时间
     *
     * @return
     */
    public static String getSystemDatatime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(new Date());
    }

    /**
     * 取得系统时间
     *
     * @return
     */
    public static String getSystemDatatimeYY() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    /**
     * 取得系统当前日期
     *
     * @return
     */
    public static String getSystemDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }



    /**
     * 字节转换成整形 *
     *
     * @param b
     * @return
     * @throws IOException
     */
    public static int byteToInt(byte[] b) throws IOException {

        ByteArrayInputStream buf = new ByteArrayInputStream(b);
        DataInputStream out = new DataInputStream(buf);
        // System.out.println("i:" + i);

        int a = out.readInt();

        // System.out.println("i:" + b);
        out.close();
        buf.close();
        return a;
    }

    public static int bytesToInt(byte b[]) {
        byte bufint[] = { b[3], b[2], b[1], b[0] };
        try {
            return byteToInt(bufint);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 字节转换成字符串
     *
     * @param b
     * @return
     * @throws IOException
     */
    public static String byteToStirng(byte[] b) throws IOException {

        ByteArrayInputStream buf = new ByteArrayInputStream(b);
        DataInputStream out = new DataInputStream(buf);
        String a = out.readUTF();
        out.close();
        buf.close();
        return a;
    }

    public final static byte[] getBytes(int s, boolean asc) {
        byte[] buf = new byte[4];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        return buf;
    }


    public static final String join(String join,String[] strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
            if(i==(strAry.length-1)){
                sb.append(strAry[i]);
            }else{
                sb.append(strAry[i]).append(join);
            }
        }

        return new String(sb);
    }

    /**
     * 获取屏幕宽度（分辨率）
     * Get screen width of Pixel
     *
     * @param activity Context
     */
    @SuppressLint("NewApi")
    public static int getScreenWidthPixelsByDisplay(Activity activity) {
        if (activity == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metric);
            return metric.widthPixels;
        } else {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.widthPixels;
        }
    }

    /**
     * 获取屏幕高度（分辨率）
     * Get screen height of Pixel
     *
     * @param activity Context
     */
    @SuppressLint("NewApi")
    public static int getScreenHeightPixelsByDisplay(Activity activity) {
        if (activity == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metric);
            return metric.heightPixels;
        } else {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.heightPixels;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(float density, int dp) {
        if (dp == 0) {
            return 0;
        }
        return (int) (dp * density + 0.5f);

    }

    public static int px2dp(Context context, int px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) Math.ceil(px / fontScale);
    }

    public static int sp2px(Context context, int sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        if (sp == 0) {
            return 0;
        }
        return (int) (sp * fontScale + 0.5f);
    }

    public static int px2sp(Context context, int px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) Math.ceil(px / fontScale);
    }

    public static String filter(String character)
    {
        character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
        return character;
    }


    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    private int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = BaseApplication.getInstance().getContext().getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }




}
