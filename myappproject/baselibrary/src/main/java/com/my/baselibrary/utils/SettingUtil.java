package com.my.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.my.baselibrary.base.BaseApplication;

/**
 * Created by AIJU on 2017-04-17.
 */

public class SettingUtil {
    /**
     * 获取声音
     * */
    public static boolean getSystemSound() {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getBoolean("sound", true);
    }

    /**
     * 获取震动
     * */
    public static boolean getSystemShake() {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getBoolean("shake", true);
    }

    /**
     * 设置声音
     * */
    public static void setSystemSound(boolean arg) {
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("sound", arg);
        editor.commit();
    }

    /**
     * 设置震动
     * */
    public static void setSystemShake(boolean arg) {
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("shake", arg);
        editor.commit();
    }

    /**
     * 设置免打扰开关
     * */
    public static void setNoDisturbing(boolean arg) {
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("NoDisturbing", arg);
        editor.commit();
    }

    /**
     * 屏蔽群消息
     * */
    public static void setChatGroupShield(String roomid, boolean shild) {
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(roomid, shild);
        editor.commit();
    }

    /** 获取是否屏蔽群消息 */
    public static boolean getChatGroupShield(String roomid) {
        SharedPreferences editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return	editor.getBoolean(roomid, false);
    }

    public static void SetDisplayMetrics(Activity activity) {

        if(BaseApplication.getInstance().getContext()==null)
        {
            HLog.w("base_1","3");
        }else{
            HLog.w("base_1","4");
        }
        try
        {
            SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                    Context.MODE_PRIVATE);
            if (sp.getInt("widthPixels", -1) != -1) {
                return;
            }
            DisplayMetrics display = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(display);

            SharedPreferences.Editor editor =  BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                            Context.MODE_PRIVATE).edit();
            editor.putInt("widthPixels", display.widthPixels);
            editor.putInt("heightPixels", display.heightPixels);
            editor.commit();
            HLog.w("base_1","1");
        }catch(Exception e)
        {
            e.printStackTrace();
            HLog.w("base_1","2");
        }
    }

    public static int getDisplaywidthPixels() {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getInt("widthPixels", -1);
    }

    public static int getDisplayheightPixels() {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getInt("heightPixels", -1);
    }


    public static String getTagString(String tag) {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getString(tag, "");
    }
    public static int getTagInt(String tag,int defaultTag) {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getInt(tag, defaultTag);
    }
    public static void setTagString(String tag, String value) {
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putString(tag, value);
        editor.commit();
    }
    public static void setTagInt(String tag, int value) {
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putInt(tag, value);
        editor.commit();
    }
    public static boolean getTagBolean(String tag) {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getBoolean(tag, false);
    }
    public static void setTagBolean(String tag, boolean value) {
        SharedPreferences.Editor editor =BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(tag, value);
        editor.commit();
    }

    public static void setPassword(String psw){
        SharedPreferences.Editor editor = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putString("Password", psw);
        editor.commit();
    }
    public static String getPassword(){
        SharedPreferences sp =BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getString("Password", "");
    }

    public static void setTagBoolean(String tag, boolean value) {
        SharedPreferences.Editor editor =BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(tag, value);
        editor.commit();
    }
    public static boolean getTagBoolean(String tag) {
        SharedPreferences sp = BaseApplication.getInstance().getContext().getSharedPreferences("aiju_setting_inf",
                Context.MODE_PRIVATE);
        return sp.getBoolean(tag, false);
    }


    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /** * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值 */
    public static   void setColor(Activity activity, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 设置状态栏透明
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 生成一个状态栏大小的矩形
                View statusView = createStatusView(activity, color);
                // 添加 statusView 到布局中
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                decorView.addView(statusView);
                // 设置根布局的参数
                ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }catch (Exception e)
        {
            e.printStackTrace();;
        }
    }


    /** * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity * @param color 状态栏颜色值 * @return 状态栏矩形条 */
    private static View createStatusView(Activity activity, int color) {
        try {
            // 获得状态栏高度
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

            // 绘制一个和状态栏一样高的矩形
            View statusView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarHeight);
            statusView.setLayoutParams(params);
            statusView.setBackgroundColor(color);
            return statusView;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
