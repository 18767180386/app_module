package com.my.baselibrary.base;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

/**
 * Created by AIJU on 2017-04-14.
 */

public class MyExceptionHandler  implements Thread.UncaughtExceptionHandler {

    private static MyExceptionHandler mHandler;
    private static Context mContext;

    private MyExceptionHandler() {
    }

    public synchronized static MyExceptionHandler getInstance(Context context) {
        if (mHandler == null) {
            mHandler = new MyExceptionHandler();
            mContext = context;
        }
        return mHandler;
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable ex) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            File file = new File("/sdcard/yhkerr.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(("time:" + System.currentTimeMillis() + "\n").getBytes());
            fos.flush();

            fos.write(sw.toString().getBytes());
            fos.flush();

            // U880

            // 获取手机的版本信息
            Field[] fields = Build.class.getFields();
            for (Field field : fields) {
                field.setAccessible(true); // 暴力反射
                String key = field.getName();
                String value = field.get(null).toString();

                fos.write((key + "=" + value + "\n").getBytes());
                fos.flush();

            }
            fos.close();
            /*
            Util.putPreferenceBoolean(mContext, "getui_back", false);
            Util.putPreferenceBoolean(mContext, "getui_flag", false);

            DataAnalysis.setCrashed();//用于自己的分析统计
            */

            new Thread() {
                public void run() {
                    Looper.prepare();
                    //出现异常更新标记
                    try{
                        Toast.makeText(mContext, "程序有点小任性，即将重启~",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){

                    }
                    Looper.loop();
                };
            }.start();

            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){

            }

            System.exit(0);

            /*

            Intent intent = new Intent(mContext, WelcomeActivity.class);
            PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            //退出程序
            AijuApplication application = AijuApplication.getContext();
            AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    restartIntent); // 1秒钟后重启应用
            application.finishActivity();
            */


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}