package com.aiju.zyb.data;

import android.content.Context;
import android.text.TextUtils;

import com.aiju.zyb.TaokeApplication;
import com.aiju.zyb.bean.User;

/**
 * Created by AIJU on 2017-04-17.
 */

public class DataManager {
    private Context context;

    private static DataManager dataManager = null;

    private UserManager userManager = null;
    private SystemSettingManager systemSettingManager = null;

    private DataManager(Context contextx) {
        this.context = contextx.getApplicationContext();
        userManager = new UserManager(context);
        systemSettingManager = new SystemSettingManager(context);
    }

    public static DataManager getInstance() {
        if (dataManager == null) {
            synchronized (DataManager.class) {
                if (dataManager == null) {
                    dataManager = new DataManager(TaokeApplication.getContext());
                }
            }
        }
        return dataManager;
    }

    /**
     * 设置用户，并将其缓存
     *
     * @param user
     */
    public void setUser(User user) {
        userManager.setUser(user);
    }


    /**
     * 保存用户信息
     */
    public void saveUser() {
        userManager.save();
    }

    /**
     * 获得登录的用户信息
     *
     * @return
     */
    public User getUser() {
        return userManager.getUser();
    }


    /***
     * 获取用户ID
     *
     * @return
     */
    public int getUserID() {
        if (getUser() != null && !getUser().getId().equals("0"))
            return Integer.valueOf(getUser().getId());
        else {
            return 0;
        }
    }


    public boolean isHasLogin() {
        return userManager.isHasLogin();
    }


    /***
     * 判断用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return userManager.isLogin();
    }

    /***
     * 判断用户是否有公司名称
     *
     * @return
     */
    public boolean isHaveCompanyName() {
        return userManager.isHaveCompanyName();
    }


    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public SystemSettingManager getSystemSettingManager() {
        return systemSettingManager;
    }

    /**
     * 用户注销，清除缓存数据
     */
    public void logout() {
        userManager.logout();
        systemSettingManager.logout();
        //  workReportManager.logout();
    }

    /**
     * 删除账号
     */
    public void delete() {

    }

    /**
     * 判断应用是否是第一次打开
     */
    public boolean getFirstOpenState(String key) {

        return  systemSettingManager.getIsFirstOpenApp(key);

    }

    /**
     * 设置应用第一次打开的状态
     */
    public void setFirstOpenState(String key,boolean flag) {
        systemSettingManager.isFirstOpenApp(key,flag);
    }

    /**
     * 设置阿里妈妈登录的cookie和token
     */
    public void setAliMMCookie(String cookie, String token) {
        userManager.setAliMMCookie(cookie, token);
    }

    /**
     * 得到阿里妈妈登录的cookie
     */
    public String getAliMMCookie() {
        return userManager.getAliMMCookie();
    }

    /**
     * 得到阿里妈妈登录的token
     */
    public String getAliMMToken() {
        return userManager.getAliMMToken();
    }

    /**
     * 判断是否登录阿里妈妈保存过cookie
     */
    public boolean isAliMMCookie() {
        if (!TextUtils.isEmpty(userManager.getAliMMCookie()))
            return true;

        return false;
    }

    /**
     * 判断是否登录阿里妈妈保存过token
     */

    public boolean isAliMMToken() {
        if (!TextUtils.isEmpty(userManager.getAliMMToken()))
            return true;

        return false;
    }
}
