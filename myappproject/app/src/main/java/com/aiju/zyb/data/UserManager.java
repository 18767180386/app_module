package com.aiju.zyb.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aiju.zyb.bean.User;
import com.aiju.zyb.data.impl.DataCenter;

import java.io.IOException;

/**
 * Created by AIJU on 2017-04-17.
 */

public class UserManager extends DataCenter<User> {

    private static String TAG = "UserManager";

    private static final String USER_PREFERENCE = "USER";
    private static final String NEW_USER_REGISTER = "new_register";
    private static final String NEW_USER_IS_BIND_SUCCESS = "new_register_is_bind_success";
    private static final String NEW_USER_HAS_BIND = "new_register_has_bind";
    private static final String IS_FIRST_OPEN = "is_first_open";
    private static final String ALI_MM_COOKIE = "ali_mm_cookie";
    private static final String ALI_MM_TOKEN = "ali_mm_token";


    /**
     * 默认的用户
     */
    public User user = null;

    private SharedPreferences sharedPreferences;

    private Context context;

    /**
     * 是否是新注册的用户
     */
    private boolean isNewRegister = false;
    /**
     * 是否进入绑定店铺页面
     */
    private boolean hasBindStore = false;
    /**
     * 进入绑定店铺页面后是否绑定成功
     */
    private boolean isBindSuccess = false;


    public UserManager(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 获取user的sp文件
     *
     * @return
     */
    private SharedPreferences getSharedPreferences() {
        return context.getApplicationContext().getSharedPreferences("dsb_oa_user_inf", Context.MODE_PRIVATE);
    }

    /**
     * 设置用户到sp文件中
     *
     * @param user
     */
    private void setUserFromSP(User user) {
        // SharedPreferences.Editor editor =  AijuApplication.getContext().getSharedPreferences("oa_user_inf", Context.MODE_PRIVATE).edit();
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        try {
            editor.putString(USER_PREFERENCE, serialize(user)).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从sp文件中获取用户名
     *
     * @return
     */
    private User getUserFromSP() {
        // EcBaoLogger.v(TAG, "获取用户缓存");
        SharedPreferences sp = getSharedPreferences();//AijuApplication.getContext().getSharedPreferences("oa_user_inf", Context.MODE_WORLD_READABLE);
        try {
            User user = deSerialization(sp.getString(USER_PREFERENCE, null));
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser() {
        if (user == null)
            user = getUserFromSP();
        return user;
    }

    public void setUser(final User oldUser) {
        user = oldUser;
        setUserFromSP(oldUser);
    }

    public boolean isNewRegister() {
        isNewRegister = getSharedPreferences().getBoolean(NEW_USER_REGISTER, false);
        return isNewRegister;
    }

    public void setNewRegister(boolean newRegister) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(NEW_USER_REGISTER, newRegister).commit();
        isNewRegister = newRegister;
    }

    /**
     * 是否有进入绑定店铺页面
     *
     * @return
     */
    public boolean isHasBindStore() {
        isNewRegister = getSharedPreferences().getBoolean(NEW_USER_HAS_BIND, false);
        return hasBindStore;
    }

    public void setHasBindStore(boolean hasBindStore) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(NEW_USER_HAS_BIND, hasBindStore).commit();
        this.hasBindStore = hasBindStore;
    }

    /**
     * 是否绑定成功
     *
     * @return
     */
    public boolean isBindSuccess() {
        isNewRegister = getSharedPreferences().getBoolean(NEW_USER_IS_BIND_SUCCESS, false);
        return isBindSuccess;
    }

    public void setBindSuccess(boolean bindSuccess) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(NEW_USER_IS_BIND_SUCCESS, bindSuccess).commit();
        isBindSuccess = bindSuccess;
    }

    /**
     * 保存用户token
     *
     * @param token
     */
    public void setUserToken(String token) {
        getUser();
        //  user.setToken(token);
        setUserFromSP(user);
    }


    /**
     * 判断是否登录（当前登录的账号id不为空）
     */
    public boolean isHasLogin() {
        return TextUtils.isEmpty(getCurrentAcountId());
    }

    /***
     * 判断用户登录
     *
     * @return
     */
    public boolean isLogin() {
        if (getUserFromSP() != null) {
            return true;
        }
        return false;
    }

    /***
     * 判断用户登录时候有公司
     *
     * @return
     */
    public boolean isHaveCompanyName() {
        return false;
    }


    /**
     * 设置当前登录账号的visitId
     *
     * @param visitId
     */
    public void setCurrentLoginAcount(String visitId) {
        setCurrentAcountId(visitId);
    }


    /**
     * 保存用户信息
     */
    public void save() {
        setUserFromSP(user);
    }

    /**
     * 用户注销
     */
    public void logout() {
        //改为删除当前用户的Sharepreference
        getSharedPreferences().edit().remove(USER_PREFERENCE).commit();
        user = null;
        sharedPreferences = null;
        isNewRegister = false;
    }

    public String getFirstOpenState() {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(IS_FIRST_OPEN, "true");
    }

    public void setFirstOpenState() {
        SharedPreferences sp = getSharedPreferences();
        sp.edit().putString(IS_FIRST_OPEN, "false").commit();

    }

    /**
     * 将登陆阿里妈妈的cookie和token保存
     */
    public void setAliMMCookie(String cookie, String token) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(ALI_MM_COOKIE, cookie);
        editor.putString(ALI_MM_TOKEN, token);
        editor.commit();
    }

    /**
     * 获取阿里妈妈登录的cookie
     */
    public String getAliMMCookie() {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(ALI_MM_COOKIE, "");
    }

    /**
     * 获取阿里妈妈登录的token
     */
    public String getAliMMToken() {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(ALI_MM_TOKEN, "");
    }
}