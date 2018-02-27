package com.aiju.zyb.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.my.baselibrary.utils.SettingUtil;
import com.my.baselibrary.utils.ToastUtil;

/**
 * Created by AIJU on 2017-06-05.
 */

public class SystemUpdateDialog extends Dialog {
    Context context;

    public SystemUpdateDialog(Context context) {
        super(context);
        this.context = context;
    }
    public SystemUpdateDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    String title="";
    public SystemUpdateDialog(Context context, int theme,String title) {
        super(context, theme);
        this.context = context;
        this.title=title;
    }

    ProgressBar mProgress = null;
    TextView tv = null;
    TextView tsize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.versionupdate);
        mProgress = (ProgressBar) findViewById(R.id.versionprogress);
        tv = (TextView) findViewById(R.id.vprogresspercent);
        tsize = (TextView) findViewById(R.id.vprogresssize);
        if(!title.equals(""))
            ((TextView) findViewById(R.id.versionprompt)).setText(title);

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width= SettingUtil.getDisplaywidthPixels()-SettingUtil.dip2px(40);
        dialogWindow.setGravity(Gravity.CENTER);
        //   lp.x = 0; // 新位置X坐标
        //    lp.y = 0; // 新位置Y坐标
        //  lp.width =width ; // 宽度
        // lp.height = height; // 高度
        //lp.alpha = 0.7f; // 透明度

//        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        //dialogWindow.setWindowAnimations(R.style.AnimationRightFade); //设置窗口弹出动画
        dialogWindow.setAttributes(lp);
    }

    public void setpress(int press,int max) {
        mProgress.setProgress(press);
        mProgress.setMax(max);
        try {
            double percent = press*100/max;
            tv.setText(percent+"%");
        } catch (Exception e) {
            e.printStackTrace();
            SystemUpdateDialog.this.dismiss();
            ToastUtil.setToast("当前网络状况恶劣，請稍候再试！");
        }

        int p=(int) (((double)press)/1024/1024*100);
        int m=(int) (((double)max)/1024/1024*100);
        tsize.setText(((double)p)/100+"M/"+((double)m)/100+"M");
    }
}

