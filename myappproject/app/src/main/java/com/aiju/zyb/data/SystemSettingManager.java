package com.aiju.zyb.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiju.zyb.TaokeApplication;

/**
 * Created by AIJU on 2017-04-17.
 */

public class SystemSettingManager {
    private static final String SETTING_PREFERENCE = "APPSETTING";
    private static final String IS_TASTE_LOGIN = "is_taste_login";
    private static final String HAS_UPLOAD_JPUSH_ID = "has_upload_jpush_id";
    private static final String HAS_TOAST_COMPANY = "has_toast_company";

    //进入app次数
    private int openAppNumbers;
    //打开app有效次数（登录成功并进入首页，成功请求一次数据）
    private int effectiveNumbers;
    //用户唯一标识udid
    private String udid;

    //jpush 注册id
    private String notifactionId = "";
    //是否上传jpush 注册id
    private boolean hasUpLoadJPushId = false;

    //上一次提示更新的时间
    private long lastShowUpdate;

    private String beginTime;

    private String endTime;

    private int homeActivityVersion;

    private boolean isTasteLogin;

    private String homeGuideVersion = "1";
    private String splashGuidlVersion = "1";
    private String loginDate = "";
    //是否弹出过创建公司或者修改公司名称
    private boolean isHasToastCompanyTip = false;

    private boolean isUseNewVersionType = true;

    private SharedPreferences sharedPreferences;
    private Context context;

    public SystemSettingManager(Context context) {
        this.context = context;
    }

    /**
     * 获取setting的sp文件
     * @return
     */
    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            //          AijuApplication.getContext().getSharedPreferences("user_inf", Context.MODE_WORLD_WRITEABLE)

            //sharedPreferences = context.getSharedPreferences(SETTING_PREFERENCE, Context.MODE_PRIVATE);
            sharedPreferences= TaokeApplication.getContext().getSharedPreferences(SETTING_PREFERENCE, Context.MODE_PRIVATE);

        }
        return sharedPreferences;
    }



    /**
     * 获取首页活动弹窗版本号
     * @return
     */
    public int getHomeActivityVersion() {
        homeActivityVersion = getSharedPreferences().getInt("homeactivityversion", 0);
        return homeActivityVersion;
    }

    /**
     * 保存首页活动弹窗版本号
     * @param homeActivityVersion
     */
    public void setHomeActivityVersion(int homeActivityVersion) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("homeactivityversion", homeActivityVersion).commit();
        this.homeActivityVersion = homeActivityVersion;
    }

    /**
     * 获取当前使用版本类型（老版财务还是新版OA财务）
     * @return
     */
    public boolean isUseNewVersion() {
        isUseNewVersionType = getSharedPreferences().getBoolean("is_use_new_version_type", true);
        return isUseNewVersionType;
    }

    /**
     * 保存当前使用版本类型（老版财务还是新版OA财务）
     * @param useOldVersion
     */
    public void setUseNewVersion(boolean useOldVersion) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean("is_use_new_version_type", useOldVersion).commit();
        this.isUseNewVersionType = useOldVersion;
    }



    /**
     * 是否经历过引导页
     * @return
     */
    public String getHomeStoreIsGuide()
    {
        homeGuideVersion = getSharedPreferences().getString("home_guide_version", "0");
        return homeGuideVersion;
    }

    public void  setHomeStoreIsGuide(String hasGuide)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("home_guide_version", hasGuide).commit();
        this.homeGuideVersion = hasGuide;
    }


    /**
     * 判断是否第一次打开
     * @param key
     * @param flag
     */
    public void  isFirstOpenApp(String key,boolean flag)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean("home_guide_version"+key, flag).commit();
    }


    public boolean getIsFirstOpenApp(String key)
    {
        return  getSharedPreferences().getBoolean("home_guide_version"+key,false);
    }




    /**
     * 获取app介绍引导页版本号
     * @return
     */
    public String getSplashGuidlVersion()
    {
        splashGuidlVersion = getSharedPreferences().getString("splash_guide_version", "0");
        return splashGuidlVersion;
    }


    /**
     * 更新app介绍引导页版本号
     * @param splash
     */
    public void setSplashGuidlVersion(String splash)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("splash_guide_version", splash).commit();
        this.splashGuidlVersion = splash;
    }

    /**
     * 获取登录日期
     * @return
     */
    public String getLoginDate()
    {
        loginDate = getSharedPreferences().getString("login_date", "");
        return loginDate;
    }

    /**
     * 保存登录时间
     * @param date
     */
    public void setLoginDate(String date)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("login_date", date).commit();
        this.loginDate = date;
    }



    /**
     * 得到上一次提示更新时间
     * @return
     */
    public long getLastShowUpdate() {
        lastShowUpdate = getSharedPreferences().getLong("last_show_update_time", 0);
        return lastShowUpdate;
    }

    /**
     *  存储本次提示更新时间
     * @param lastShowUpdate
     */
    public void setLastShowUpdate(long lastShowUpdate) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong("last_show_update_time", lastShowUpdate).commit();
        this.lastShowUpdate = lastShowUpdate;
    }

    /**
     * 得到打开app的有效次数
     * @return
     */
    public int getEffectiveNumbers() {
        effectiveNumbers = getSharedPreferences().getInt("effectivenumbers", 0);
        return effectiveNumbers;
    }

    public void setEffectiveNumbers(int effectiveNumbers) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("effectivenumbers", effectiveNumbers).commit();
        this.effectiveNumbers = effectiveNumbers;
    }

    /**
     * 得到打开app的次数（非有效次数）
     * @return
     */
    public int getOpenAppNumbers() {
        openAppNumbers = getSharedPreferences().getInt("opennumbers", 0);
        return openAppNumbers;
    }

    public void setOpenAppNumbers(int openAppNumbers) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("opennumbers", openAppNumbers).commit();
        this.openAppNumbers = openAppNumbers;
    }

    public String getNotifactionId(){
        if (notifactionId == null || "".equals(notifactionId))
            notifactionId = getNotifactionIdFromSP();
        return notifactionId;
    }

    private String getNotifactionIdFromSP() {
        notifactionId = getSharedPreferences().getString("notifactionId", "");
        return notifactionId;
    }

    public void setNotifactionId(String notifaction_id)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("notifactionId", notifaction_id).commit();
        // SharedPreferences.Editor editor =  AijuApplication.getContext().getSharedPreferences("user_inf", Context.MODE_WORLD_WRITEABLE).edit();
        // editor.putString("notifactionId", notifaction_id).commit();
        this.notifactionId = notifaction_id;
    }

    /**
     * 保存上传JPush注册成功后得到的id并且成功的状态
     * @param hasUpLoad
     */
    public void setHasUpLoadJPushId(boolean hasUpLoad)
    {
        //  SharedPreferences.Editor editor =  AijuApplication.getContext().getSharedPreferences("user_inf", Context.MODE_WORLD_WRITEABLE).edit();
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(HAS_UPLOAD_JPUSH_ID, hasUpLoad);
        editor.commit();
        hasUpLoadJPushId = hasUpLoad;
    }

    /**
     * 获取上传JPush注册成功后的id是否成功的状态
     * @return
     */
    public boolean IsHasUpLoadJPushId()
    {
        // SharedPreferences sp = AijuApplication.getContext().getSharedPreferences("user_inf", Context.MODE_WORLD_READABLE);
        //  SharedPreferences.Editor editor = getSharedPreferences().edit();
        hasUpLoadJPushId = getSharedPreferences().getBoolean(HAS_UPLOAD_JPUSH_ID, false);
        return hasUpLoadJPushId;
    }

    /**
     * 设置创建公司or修改公司名称弹窗的情况
     * @param hasToastCompanyTip
     */
    public void setHasToastCompany(boolean hasToastCompanyTip)
    {
        SharedPreferences.Editor editor =  getSharedPreferences().edit();
        editor.putBoolean(HAS_TOAST_COMPANY, hasToastCompanyTip).commit();
        isHasToastCompanyTip = hasToastCompanyTip;
    }

    /**
     * 获取是否弹出过创建公司or修改公司名称的弹出
     * @return
     */
    public boolean getHsaToastCompany()
    {
        isHasToastCompanyTip = getSharedPreferences().getBoolean(HAS_TOAST_COMPANY, false);
        return isHasToastCompanyTip;
    }



    public String getUdid() {
        udid = getSharedPreferences().getString("user_udid", "");
        return udid;
    }

    public void setUdid(String newUdid) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("user_udid", newUdid).commit();
        this.udid = newUdid;
    }


    public String getBeginTime() {
        beginTime = getSharedPreferences().getString("begin_time", "1");
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("begin_time", beginTime).commit();
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        endTime = getSharedPreferences().getString("end_time", "");
        return endTime;
    }

    public void setEndTime(String endTime) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("end_time", endTime).commit();
        this.endTime = endTime;
    }

    public boolean isTasteLogin() {
        isTasteLogin = getSharedPreferences().getBoolean(IS_TASTE_LOGIN, false);
        return isTasteLogin;
    }

    public void setIsTasteLogin(boolean tasteLogin) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(IS_TASTE_LOGIN, tasteLogin).commit();
        this.isTasteLogin = tasteLogin;
    }


    /**
     * 用户注销
     */
    public void logout() {
        getSharedPreferences().edit().remove("user_udid").commit();
        getSharedPreferences().edit().remove("end_time").commit();
        getSharedPreferences().edit().remove("begin_time").commit();
        getSharedPreferences().edit().remove("effectivenumbers").commit();
        getSharedPreferences().edit().remove("homerequestnumber").commit();

        getSharedPreferences().edit().remove("homeactivityversion").commit();
        getSharedPreferences().edit().remove("last_show_update_time").commit();
        getSharedPreferences().edit().remove(IS_TASTE_LOGIN).commit();
        getSharedPreferences().edit().remove("notifactionId").commit();
        getSharedPreferences().edit().remove(HAS_UPLOAD_JPUSH_ID).commit();
        getSharedPreferences().edit().remove("is_use_new_version_type").commit();
        getSharedPreferences().edit().remove("login_date").commit();
        getSharedPreferences().edit().remove(HAS_TOAST_COMPANY).commit();


//        getSharedPreferences().edit().clear().commit();
        sharedPreferences = null;
        udid = null;
        openAppNumbers = 0;
        effectiveNumbers = 0;
        homeActivityVersion = 0;
        isTasteLogin = false;
        hasUpLoadJPushId = false;
        notifactionId = "";
        loginDate = "";
        isHasToastCompanyTip = false;
    }

}
