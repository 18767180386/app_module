package com.aiju.zyb.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.aiju.zyb.TaokeApplication;

/**
 * Created by AIJU on 2017-05-26.
 */

public class   Utils {

    /**
     * 设置drawable图片
     *
     * @param rid
     * @param v
     * @param type
     */
    public static void setDrable(int rid, View v, int type) {
        Drawable drawable = TaokeApplication.getContext().getResources().getDrawable(rid);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((TextView) v).setCompoundDrawablePadding(5);
        ((TextView) v).setCompoundDrawables(null, null, type == 0 ? null : drawable, null);
    }


    /**
     * 获取app信息
     *
     * @param context
     * @return
     */
    public static String getAppInfo(Context context, int type) {
        try {
            PackageInfo pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String appName = pkg.applicationInfo.loadLabel(context.getPackageManager()).toString();
            String versionName = pkg.versionName;
            String versionCode = pkg.versionCode + "";
            if (type == 0) {
                return appName;
            } else if (type == 1) {
                return versionName;
            } else if (type == 2) {
                return versionCode;
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    private String getAppInfso(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "   " + versionName + "  " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

}
