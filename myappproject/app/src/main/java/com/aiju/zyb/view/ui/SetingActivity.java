package com.aiju.zyb.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeUpdateBean;
import com.aiju.zyb.manage.VersionManager;
import com.aiju.zyb.view.BaseActivity;
import com.aiju.zyb.view.widget.BannerGroup;
import com.aiju.zyb.view.widget.BannerItem;
import com.aiju.zyb.view.widget.BannerMenuChart;

import com.my.baselibrary.utils.GlideTool;
import com.my.baselibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SetingActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout seting_content;
    private LinearLayout login_li;
    private int  type_tip=-1;
    private String[] tip={"已是最新版本","发现新版本","正在检测版本，稍后再试","未知错误"};
    private  VersionManager version=null;
    @Override
    protected  void initView() {
        seting_content=(LinearLayout)findViewById(R.id.seting_content);
        login_li=(LinearLayout)findViewById(R.id.login_li);
        login_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });
        commonView(seting_content,tip[0]);
        loadData();
        version=new VersionManager(this);
        version.initiate(0, new VersionManager.IRequestDataStatus() {
            @Override
            public void requestSuccess(TaokeUpdateBean info) {
                if(info.getIsnewVersion().equals("0"))
                {
                    type_tip=0;
                    commonView(seting_content,tip[0]);
                }else{
                    type_tip=1;
                    commonView(seting_content,tip[1]);
                }
            }

            @Override
            public void requestFail() {

            }
        });
    }



    /**
     *
     *
     */
    private void loadData()
    {

    }


    /**
     * 退出登录
     */
    public void logout(View view) {


    }


    private  void  commonView(ViewGroup parentView,String tip)
    {
        parentView.removeAllViews();
        List<BannerItem> takeItemsSet = new ArrayList<>();
        takeItemsSet.add(new BannerItem(R.mipmap.chart_7, "订单", R.mipmap.turn_right_chart));
        final BannerGroup takeGroupSet = new BannerGroup("", takeItemsSet);
        BannerMenuChart takeBannerSet = new BannerMenuChart(this, takeGroupSet, new BannerMenuChart.onBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                switch (position) {
                    case 0:
                      // jumpActivity(0);
                        break;
                }
            }
        });


       // parentView.addView(takeBannerSet);


        List<BannerItem> takeItemsMore = new ArrayList<>();
        takeItemsMore.add(new BannerItem(0, "清除缓存", R.mipmap.turn_right_chart));
        takeItemsMore.add(new BannerItem(0, "检测版本更新",tip));
        // takeItemsMore.add(new BannerItem(R.mipmap.version_tip_imgs, "版本说明", R.mipmap.turn_right_chart));
        final BannerGroup takeGroupMore = new BannerGroup("", takeItemsMore);
        BannerMenuChart takeBannerMore = new BannerMenuChart(this, takeGroupMore, new BannerMenuChart.onBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                switch (position) {
                    case 0:
                        jumpActivity(0);
                        break;
                    case 1:
                        jumpActivity(1);
                        break;
                    case 2:
                        // jumpActivity(7);
                        break;
                }
            }
        });
        parentView.addView(takeBannerMore);

    }

    /***
     *
     *
     * 页面跳转
     * @param type
     */
    private  void   jumpActivity(int type)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch(type)
        {
            case 0:  //清除缓存
                GlideTool.clearCacheMemory();
                break;
            case 1: //检测版本
                versionUpdate();
                break;

        }

    }


    /**
     *
     * 检测版本更新
     */
    private  void versionUpdate()
    {
        if(type_tip==-1)
        {
            ToastUtil.setToast(tip[2]);
        }else if(type_tip==0){
            ToastUtil.setToast(tip[0]);
        }else if(type_tip==1)
        {
            version.showUpdateTip();
        }else{
            ToastUtil.setToast(tip[3]);
        }
    }




    @Override
    protected void initData() {


    }

    @Override
    protected String getTextTitle() {
        return "设置";
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_seting;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // CallbackContext.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
