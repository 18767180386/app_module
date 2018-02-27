package com.my.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.my.baselibrary.utils.HLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AIJU on 2017-04-14.
 */

public class BaseApplication extends Application{
    private static String TAG = BaseApplication.class.getName();
    public static Handler handler = new Handler(Looper.getMainLooper());
    public static String iemiCode;
    public static String versions = "-1";
    private boolean isGetErrorInfo = false;// 是否开启错误日志

    private List<Activity> activityList = new ArrayList<>();
    private List<Activity> picactivityList = new ArrayList<>();
    private static BaseApplication instance;//baseapplication 对象
    private static Context sContext;//全局application context
    //网络连接状态
    private boolean networkState = true;
    private static boolean isDexAvailable = false;
    public synchronized static BaseApplication getInstance() {
        if(instance==null)
        {
            instance=new BaseApplication();
        }
        return instance;
    }



    public  Context getContext() {
        /*
        Class<?> herosClass = Heros.class;
        try {
            Method m1 = herosClass.getMethod("setName",String.class);
            Method m2 = herosClass.getMethod("getName");
            Object userInfo = herosClass.newInstance();
            m1.invoke(userInfo,"影魔");
            System.out.println("调用set方法："+userInfo);
            System.out.println("调用get方法："+m2.invoke(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        if (sContext == null) {
            try {
               // Class cls = Class.forName("com.aiju.yhk.TaokeApplication");
               // Method method=cls.getMethod("getContext");
               // method.invoke(cls)
               // sContext = createPackageContext("com.aiju.yhk.TaokeApplication", CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY);
                sContext = (Context) Class.forName("com.aiju.zyb.TaokeApplication").getDeclaredMethod("getContext").invoke(null);
                if(sContext==null)
                {
                    HLog.w("base_content","1");
                }else{
                    HLog.w("base_content","2");
                }
               //获取setting包的的Context

               // 通过类名获取类。
               // Class serviceManager = Class.forName("Android.os.ServiceManager");
               // 获取方法
               // Method method = serviceManager.getMethod("getService", String.class);
              //  调用方法
               /// method.invoke(serviceManager.newInstance(), "phone");
              //  Context mmsCtx = createPackageContext("com.aiju.yhk",Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
//使用setting的classloader加载com.android.settings.ManageApplications类
              //  Class<?> maClass = Class.forName("com.aiju.yhk.TaokeApplication", true, mmsCtx.getClassLoader());
                //创建它的一个对象
              //  Object maObject = maClass.newInstance();
               /// sContext=(Context)maObject;

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return sContext;
    }
    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    // private static DemoHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        instance = this;
        //这里的操作是为了当主module为ecbao是，baseapplication的context为null，在工具类里调用basecontext.getcontext结果为null，因此作如下判断，如果为null
        //的时候，我们就去获取aijuapplication的context做替换


      //  TCAgent.init(this.getApplicationContext());
        //TCAgent.setReportUncaughtExceptions(true);

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


      //  iemiCode = Util.getImeiCode(getApplicationContext());


        // initJpush();
        //  initImageLoader(getApplicationContext());



        /***
         *
         * 百度地图
         */
      // SDKInitializer.initialize(getApplicationContext());




        /*
        AlibabaSDK.asyncInit(this, new InitResultCallback() {

            @Override
            public void onSuccess() {
                Log.w("mayongge", "初始化成功");
            }

            @Override
            public void onFailure(int code, String message) {
                Log.w("mayongge", "初始化异常，code = " + code + ", info = " + message);
            }

        });

        */


    }


    public void loginOut() {
    }

    public boolean isNetworkState() {
        return networkState;
    }

    public void setNetworkState(boolean networkState) {
        this.networkState = networkState;
    }


    public SharedPreferences getSetting() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SharedPreferences getSimpleSetting() {
        return BaseApplication.getInstance().getContext().getSharedPreferences("simpleSetting",
                MODE_PRIVATE);
    }


    // 缓存目录
    public static final String IMAGE_CACHE = "dianshangbao/cache/images/";


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
