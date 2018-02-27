package com.my.baselibrary.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by AIJU on 2017-04-17.
 */

public class HLog {
    public static int LOG_LEVEL = 6;
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;


    public static void e(String tag,String msg){
        if(LOG_LEVEL>ERROR)
            Log.e(tag, msg);
    }

    public static void w(String tag,String msg){
        if(!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            if (LOG_LEVEL > WARN)
                Log.w(tag, msg);
        }
    }
    public static void i(String tag,String msg){
        if(LOG_LEVEL>INFO)
            Log.i(tag, msg);
    }
    public static void d(String tag,String msg){
        if(LOG_LEVEL>DEBUG)
            Log.d(tag, msg);
    }
    public static void v(String tag,String msg){
        if(LOG_LEVEL>VERBOS)
            Log.v(tag, msg);
    }
}
