package com.my.baselibrary.utils;

import android.widget.Toast;

import com.my.baselibrary.base.BaseApplication;

/**
 * Created by AIJU on 2017-05-01.
 */

public class ToastUtil {
    public static Toast toast;

    public static void setToast(String str) {

        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance().getContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
