package com.aiju.zyb.view.photo;

import android.os.Build;
import android.view.View;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
/**
 * Created by john on 2018/2/8.
 */

public class Compat {
    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            SDK16.postOnAnimation(view, runnable);
        } else {
            view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
        }
    }
}
