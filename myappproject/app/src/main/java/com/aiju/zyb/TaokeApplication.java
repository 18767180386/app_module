package com.aiju.zyb;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;


import com.bumptech.glide.Glide;
import com.my.baselibrary.base.MyExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AIJU on 2017-04-14.
 */

public class TaokeApplication extends Application {
    private static String TAG = TaokeApplication.class.getName();
    public static Handler handler = new Handler(Looper.getMainLooper());
    public static String iemiCode;
    public static String versions = "-1";
    private boolean isGetErrorInfo = true;// 是否开启错误日志

    private List<Activity> activityList = new ArrayList<>();
    private List<Activity> picactivityList = new ArrayList<>();
    private static TaokeApplication instance;
    private static Context sContext;//全局application context
    //网络连接状态
    private boolean networkState = true;
    private static boolean isDexAvailable = false;
    public synchronized static TaokeApplication getInstance() {
        return instance;
    }
    public static Context getContext() {
        return sContext.getApplicationContext();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        instance = this;
        registerActivityListener();
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            versions = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        if (isGetErrorInfo) {
            Thread.setDefaultUncaughtExceptionHandler(MyExceptionHandler
                    .getInstance(getApplicationContext()));
        }


        /*
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                MemberSDK.turnOnDebug();
               // Toast.makeText(TaokeApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
               // Toast.makeText(TaokeApplication.this, "初始化失败,错误码="+code+" / 错误消息="+msg, Toast.LENGTH_SHORT).show();
            }
        });
        */


    }

    public SharedPreferences getSetting() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SharedPreferences getSimpleSetting() {
        return TaokeApplication.getContext().getSharedPreferences("simpleSetting",
                Context.MODE_PRIVATE);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        activityList.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        activityList.add(a);
    }

    /**
     * 关闭Activity列表中的所有ActivityfinishActivity
     */

    /**
     * 关闭Activity列表中的所有ActivityfinishActivity
     */
    public void finishActivity() {
        try {
            for (Activity activity : activityList) {
                if (null != activity) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishAllActivity() {
        try {
            for (Activity activity : activityList) {
                activity.finish();
            }
            activityList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片activity
     *
     * @param a
     */
    public void addPicActiviy(Activity a) {
        picactivityList.add(a);
    }

    public void finishPicActivity() {
        for (Activity activity : picactivityList) {
            if (null != activity) {
                activity.finish();
            }
        }
    }
    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    addActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activityList && activityList.isEmpty()) {
                        return;
                    }
                    if (activityList.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        removeActivity(activity);
                    }
                }
            });
        }

    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                // 进行资源释放操作
                Glide.get(this).clearMemory();//
                break;
        }

           /*
        OnTrimMemory的参数是一个int数值，代表不同的内存状态：
        TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
        TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
        TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
        TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。
        以上4个是4.0增加
        TRIM_MEMORY_RUNNING_CRITICAL：内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
        TRIM_MEMORY_RUNNING_LOW：内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
        TRIM_MEMORY_RUNNING_MODERATE：内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存

        */
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // 进行资源释放操作
        try {
            Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
            // Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
            //  Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
            // ThreadTool.getIns().CloseExecutorService();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        super.onTerminate();

    }
}
