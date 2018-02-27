package com.aiju.zyb.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.my.baselibrary.utils.StatusBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by AIJU on 2017-04-14.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {
    public SystemBarTintManager tintManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tintManager = new SystemBarTintManager(this);
        StatusBar.getIntanst().statusBarHeight(this);
        setContentView(getLayoutId());
        initView();
        initListener();
        initData();
    }

    /*
    @Override
    public void setContentView(int layoutResID) {
        // android:background="@color/colorAccent"
        setContentView(layoutResID, true, true, R.color.colorAccent);

        if (layoutResID == R.layout.activity_main2) {
            super.setContentView(layoutResID);
            setNoStautsBar();
        } else {
            // setContentView(layoutResID, true, true, R.mipmap.status_bar_bg);

            setContentView(layoutResID, true, true, R.color.color_28b4f4);
        }

    }

    public void setContentView(int layoutResID, boolean isHade) {
        super.setContentView(layoutResID);
        tintManager.setStatusBarTintEnabled(true);

    }


    public void setContentView(int layoutResID, int statuColor) {
        setContentView(layoutResID, true, true, statuColor);
    }


    public void setContentView(int layoutResID, boolean isInitStatusBar, boolean isFitsSystemWindows, int statuColor) {
        if (isInitStatusBar) {
            super.setContentView(layoutResID);
            setStatusBar(statuColor);
        } else {
            super.setContentView(layoutResID);
            return;
        }
        if (isFitsSystemWindows)
            setRootView(true);
        else
            setRootView(false);
    }

    public void setNoStautsBar() {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(false);
        rootView.setClipToPadding(false);
        tintManager.setStatusBarTintEnabled(false);
        tintManager.setNavigationBarTintEnabled(false);
        rootView.requestLayout();
//            tintManager.setmStatusBarAvailable(false);
        this.getWindow().getDecorView().requestLayout();
    }

    protected void setStatusBar(int statuColor) {
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(false);
        tintManager.setTintResource(statuColor);
    }


    public void setRootView(boolean isHade) {
        if (isHade) {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
            rootView.requestLayout();
//            tintManager.setmStatusBarAvailable(true);
            this.getWindow().getDecorView().requestLayout();
        } else {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(false);
            tintManager.setStatusBarTintEnabled(false);
            tintManager.setNavigationBarTintEnabled(false);
            rootView.requestLayout();
//            tintManager.setmStatusBarAvailable(false);
            this.getWindow().getDecorView().requestLayout();
        }
    }

  */

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract void initListener();

    protected abstract void initView();
}
