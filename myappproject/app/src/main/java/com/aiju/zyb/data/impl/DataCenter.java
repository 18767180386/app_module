package com.aiju.zyb.data.impl;

/**
 * Created by AIJU on 2017-04-17.
 */

import android.content.Context;
import android.content.SharedPreferences;

public abstract class DataCenter<T> extends DataImpl<T> {
    private final static String CURRENT_ACOUNT_ID = "current_id";
    private final static String CURRENT_PREFERENCE = "current_preference";
    private String mCurrentAcountId;
    private Context context;
    private SharedPreferences dataSharedPreferences;

    public DataCenter(Context context) {
        this.context = context;
    }

    /**
     * 获取当前登录账号的sp文件
     * @return
     */
    private SharedPreferences getSharedPreferences() {
        if (dataSharedPreferences == null) {
            dataSharedPreferences = context.getSharedPreferences(CURRENT_PREFERENCE, Context.MODE_PRIVATE);
        }
        return dataSharedPreferences;
    }

    public String getCurrentAcountId() {
        mCurrentAcountId = getSharedPreferences().getString(CURRENT_ACOUNT_ID, "");
        return mCurrentAcountId;
    }

    public void setCurrentAcountId(String currentAcountId) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(CURRENT_ACOUNT_ID, currentAcountId).commit();
        this.mCurrentAcountId = currentAcountId;
    }
    /**
     * 用户注销
     */
    public void logout() {
        //改为删除当前Sharepreference
        getSharedPreferences().edit().clear().commit();
        mCurrentAcountId = null;
        dataSharedPreferences = null;
    }
}