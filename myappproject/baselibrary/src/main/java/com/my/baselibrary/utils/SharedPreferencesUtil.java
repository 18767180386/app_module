package com.my.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.my.baselibrary.base.BaseApplication;

import java.util.Set;

/**
 * Created by AIJU on 2017-04-17.
 */

public class SharedPreferencesUtil {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static SharedPreferencesUtil preferencesInstance;
    /**
     * 是否是第一次初始化智媒体
     */
    public static String IS_INIT_NOTICE_FIRST = "is_init_notice_first";
    /**
     * 更新区域信息
     */
    public static final String IS_UPDATE_AREA = "IsUpdateAreaV2";
    public static SharedPreferencesUtil getInstance() {
        if( preferencesInstance == null) {
            preferencesInstance = new SharedPreferencesUtil();
        }

        if (mSharedPreferences == null) {
            mSharedPreferences = BaseApplication.getInstance().getContext()
                    .getSharedPreferences(Constants.CONFIG_PREFERENCE_NAME,
                            Context.MODE_PRIVATE);
        }
        if (editor == null) {
            editor = mSharedPreferences.edit();
        }
        return preferencesInstance;
    }

    public static boolean contains(String key) {
        getInstance();
        return mSharedPreferences.contains(key);
    }

    public static int getIntValueByKey(String key, int defValue) {
        getInstance();
        return mSharedPreferences.getInt(key, defValue);
    }

    public static String getStringValueByKey(String key, String defValue) {
        getInstance();
        return mSharedPreferences.getString(key, defValue);
    }

    public static float getfloatValueByKey(String key, Float defValue) {
        getInstance();
        return mSharedPreferences.getFloat(key, defValue);
    }

    public static boolean getBooleanValueByKey(String key, Boolean defValue) {
        getInstance();
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public static long getLongValueByKey(String key, long defValue) {
        getInstance();
        return mSharedPreferences.getLong(key, defValue);
    }

    public static void setBooleanValueByKey(String key, boolean value) {
        getInstance();
        editor.putBoolean(key, value).commit();
    }

    public static void setStringValueByKey(String key, String value) {
        getInstance();
        editor.putString(key, value).commit();
    }

    public static void setIntValueByKey(String key, int value) {
        getInstance();
        editor.putInt(key, value).commit();
    }

    public static void setFloatValueByKey(String key, Float value) {
        getInstance();
        editor.putFloat(key, value).commit();
    }

    public static void setLongValueByKey(String key, Long value) {
        getInstance();
        editor.putLong(key, value).commit();
    }

    public static void setStringSetByKey(String key,Set<String> set){
        getInstance();
        editor.putStringSet(key, set).commit();
    }

    public static String getDeviceUUIDByKey(String key, String defValue) {
        getInstance();
        return mSharedPreferences.getString(key, defValue);
    }

    public static void setDeviceUUIDByKey(String key, String Value){
        getInstance();
        editor.putString(key, Value).commit();
    }
}
