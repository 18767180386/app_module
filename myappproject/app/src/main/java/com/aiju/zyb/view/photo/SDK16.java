package com.aiju.zyb.view.photo;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by john on 2018/2/8.
 */

public class SDK16 {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void postOnAnimation(View view, Runnable r) {
        view.postOnAnimation(r);
    }
}
