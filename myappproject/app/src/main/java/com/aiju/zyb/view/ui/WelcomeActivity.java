package com.aiju.zyb.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aiju.zyb.MainActivity;
import com.aiju.zyb.R;
import com.aiju.zyb.TaokeApplication;
import com.my.baselibrary.utils.SettingUtil;
import com.my.baselibrary.utils.Util;

public class WelcomeActivity extends FragmentActivity {
    private int delayTime=2000;
    private ImageView img_start;
    private String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        SettingUtil.SetDisplayMetrics(this);
        img_start=(ImageView)findViewById(R.id.img_start);
       // img_start.getLayoutParams().width=SettingUtil.getDisplaywidthPixels();//
       // img_start.getLayoutParams().height=SettingUtil.getDisplayheightPixels();
      //  Glide.with(this).load(R.mipmap.start).into(img_start);
        key= Util.getAppVersionName(TaokeApplication.getContext());
        TaokeApplication.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                /*
                if(!DataManager.getInstance().getFirstOpenState(key)) {
                    DataManager.getInstance().setFirstOpenState(key,true);
                    startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
                */
            }
        },delayTime);
    }
}
