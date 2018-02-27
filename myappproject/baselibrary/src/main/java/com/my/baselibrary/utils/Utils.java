package com.my.baselibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.SoundEffectConstants;
import android.view.View;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by AIJU on 2017-04-17.
 */

public class Utils {
    public static boolean DEBUG = true;
    public static final int SOUND_KEYSTONE_KEY = 1;
    public static final int SOUND_ERROR_KEY = 0;
    public static final int LARGE_NUMBER_BASE = 100000;

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <E> ArrayList<E> newArrayList(E... elements) {
        int capacity = (elements.length * 110) / 100 + 5;
        ArrayList<E> list = new ArrayList<E>(capacity);
        Collections.addAll(list, elements);
        return list;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static long readSystemAvailableSize() {
        String path = "/data";
        StatFs sf = new StatFs(path);
        long blockSize = sf.getBlockSize();
        //long blockCount = sf.getBlockCount();
        //QiaQiaLog.d("available count", "available count: " + sf.getAvailableBlocks());
        long availCount = sf.getAvailableBlocks();
        return blockSize * availCount / 1024;
    }

    public static void playKeySound(View view, int soundKey) {
        if (null != view) {
            if (soundKey == SOUND_KEYSTONE_KEY) {
                view.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
            } else if (soundKey == SOUND_ERROR_KEY) {
            }
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public static float getMSizeFromK(int kSize) {
        return kSize / 1024f;
    }

    public static String longToDate(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return sdf.format(timeMillis);
    }

    public static byte[] readStreamToByteArray(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[512 * 1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    public static void saveInputstreamToFile(InputStream is, File targetFile) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(targetFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8 * 1024];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
        }
    }

    public static String getStringMD5(String key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(key.getBytes());
        //important: use Base64.URL_SAFE flag to avoid "+" and "/"
        return new String(Base64.encode(md5.digest(), Base64.URL_SAFE));
    }

    public static String getCacheFolder(Context context) {
        File cacheFolder = new File(context.getCacheDir().getAbsolutePath() + File.separator + "app_icons");
        if (!cacheFolder.exists()) {
            cacheFolder.mkdir();
        }
        return cacheFolder.getAbsolutePath();
    }

    public static int getMemoryClass(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        //128m for mibox
//		QiaQiaLog.e(TAG, "Memory class: " + memoryClass);
        return memoryClass;
    }

    public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return byte2HexStr(rawHmac);
    }

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
        }
        return sb.toString().toLowerCase().trim();
    }

    public static Integer getKeyByValue(Map<Integer, String> map, Object value) {
        Integer key = -1;
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
            String obj = entry.getValue();
            if (obj != null && obj.equals(value)) {
                // break as find the first key, assuming key and value are one-to-one
                key = (Integer) entry.getKey();
                break;
            }
        }
        return key;
    }

    public static String largeNumberPattern(int largeNumber) {
        String patternedString = "0";
        if (largeNumber > 0) {
            String unit = null;
            if (largeNumber >= LARGE_NUMBER_BASE) {
                largeNumber /= 10000;
                unit = "万";
            }
            DecimalFormat df = new DecimalFormat("#,###");
            patternedString = df.format(largeNumber);
            if (null != unit) {
                patternedString += unit;
            }
        }
        return patternedString;
    }

    public static String getFileNameExtension(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            int end = fileName.lastIndexOf(".");
            if (end >= 0) {
                return fileName.substring(end + 1);
            }
        }
        return null;
    }

    public static String covertStreamToString(InputStream is, String encode) throws IOException {
        if (null != is) {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            int n;
            while ((n = is.read(b)) != -1) {
                if (encode == null) {
                    out.append(new String(b, 0, n));
                } else {
                    out.append(new String(b, 0, n, encode));
                }
            }
            return out.toString();
        }
        return null;
    }


    public static String getFilePath(String url) {
        if (!TextUtils.isEmpty(url)) {
            int end = url.lastIndexOf("/");
            if (end >= 0) {
                return url.substring(0, end + 1);
            }
        }
        return null;
    }

    public static String getFileName(String url) {
        if (!TextUtils.isEmpty(url)) {
            int end = url.lastIndexOf("/");
            if (end >= 0) {
                return url.substring(end + 1);
            }
        }
        return null;
    }

    public static boolean containNonEnglishChar(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] > 255 || charArray[i] < 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkFileValid(String fullPath) {
        if (!TextUtils.isEmpty(fullPath)) {
            File file = new File(fullPath);
            if (file.exists() && file.isFile()) {
                return true;
            }
        }
        return false;
    }


    public static boolean isEmpty(ArrayList list) {
        return list != null && list.size() > 0;
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
     * 状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static boolean isHoneyComb() {
        int current = Build.VERSION.SDK_INT;
        return current >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getVersion(Context context) {//获取版本号
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "2.0.2";
        }
    }

    /**
     * 获取系统版本号
     * @return
     */
    public static String getApiSdkVersion() {
        String osVersion = "";
        try {
            osVersion = Build.VERSION.RELEASE;
        } catch (NumberFormatException e) {
            osVersion = "0.0.0";
        }
        return osVersion;

    }

    /**
     * 获取手机型号
     * @return
     */
    public static String getMobileModel()
    {
        String model = "";
        model = Build.MODEL;
        return model;
    }


    /**
     * 生成表示文件大小的字符串
     *
     * @param sizeInB 以B为单位的大小
     * @return 以KB/MB/GB为单位的大小字符串
     */
    public static String generateFileSize(long sizeInB) {
        if (sizeInB < 1024) {
            return sizeInB + "B";
        } else if (sizeInB < (1024 * 1024)) {
            return (10 * sizeInB / 1024) / 10.0 + "KB";
        } else if (sizeInB < (1024 * 1024 * 1024)) {
            return (10 * sizeInB / (1024 * 1024)) / 10.0 + "MB";
        } else {
            return (10 * sizeInB / (1024 * 1024 * 1024)) / 10.0 + "GB";
        }
    }

    /**
     * 执行Linux命令，并返回执行结果。
     */
    public static String exec(String[] args) {
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    //保留两位小数点
    public static String expireStringCut(String string) {
        String cut = null;
        int end = string.indexOf(".");
        if (string.length() > end + 3)
            cut = string.substring(0, end + 3);
        else
            cut = string;
        return cut;
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
}
