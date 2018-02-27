package com.aiju.zyb.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aiju.zyb.MainActivity;
import com.aiju.zyb.R;
import com.my.baselibrary.utils.SettingUtil;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private ArrayList<View> dots;
    private int imageIds[];
    private String[] titles;
    private ArrayList<ImageView> images;
    private int oldPosition = 0;
    private int currentItem;
    private LinearLayout mBottomLayout;
    private Button mTasteRightBtn;
    private int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initPagerRes();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        //    mLoginBtn = (Button) findViewById(R.id.splash_login_btn);
        mTasteRightBtn = (Button) findViewById(R.id.splash_login_btn);
        mBottomLayout = (LinearLayout) findViewById(R.id.splash_layout);
        mViewPager.setAdapter(new MyViewPagerAdapter(addView()));
        mViewPager.setOnPageChangeListener(new MyListener());
        //设置ViewPager的滑动监听,为了滑动到最后一页,继续滑动实现页面的跳转
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            float startX;

            float endX;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();

                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                       //获取屏幕的宽度
                        int width = SettingUtil.getDisplaywidthPixels();
                        //根据滑动的距离来切换界面
                        if (currentPage == images.size()-1 && startX - endX >= (width / 5)) {

                            goToMainActivity();//切换界面
                        }

                        break;
                }
                return false;
            }
        });

    }


    private void goToMainActivity() {
        Intent  intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private List<View> addView() {
        List<View> list = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView view = new ImageView(this);
            view.setLayoutParams(params);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(imageIds[i]);
          //  Glide.with(this).load(imageIds[i]).into(view);
            view.setTag(i);
            list.add(view);
        }
        return list;
    }


    /**
     * 根据不同的场景（轮播的图片数量），初始化资源
     *
     * @param
     * @param
     */
    public void initPagerRes() {

        //下标点
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.v_dot1));
        dots.add(findViewById(R.id.v_dot2));
        dots.add(findViewById(R.id.v_dot3));
        dots.add(findViewById(R.id.v_dot4));

        imageIds = new int[]{
                R.mipmap.splash_1,
                R.mipmap.splash_2,
                R.mipmap.splash_3,
                R.mipmap.splash_4,
        };
        //初始化轮播图片资源
        images = new ArrayList<>();
        for (int imageId : imageIds) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageId);
           // Glide.with(this).load(imageId).into(imageView);
            images.add(imageView);
        }
    }

    private List<View> mListViews;
    class MyViewPagerAdapter extends PagerAdapter{


        public MyViewPagerAdapter(List<View> list) {
            mListViews =list;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;//官方提示这样写
        }
    }




    class MyListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int position) {
            // TODO 根据当前位置pos来进行相应的操作
            // EcBaoLogger.e("onPageScrollStateChanged", String.valueOf(position));

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
           // EcBaoLogger.e("onPageScrolled", String.valueOf(arg0));
        }

        @Override
        public void onPageSelected(int arg0) {
            currentItem=arg0;
            dots.get(oldPosition).setBackgroundResource(R.drawable.page_control);
            dots.get(arg0).setBackgroundResource(R.drawable.page_control_chick);
            oldPosition = arg0;
          //  EcBaoLogger.e("onPageSelected", String.valueOf(arg0) +"-----"+(viewList.size()-1));
            //currentpage后面要用
            currentPage=arg0;
            /*
            if(position==2){
                //设置可见,如果用按钮也可以实现activity的跳转,两种跳转功能都具备
                bt.setVisibility(View.VISIBLE);
                bt.setOnClickListener(IntroActivity.this);
            }else {
                bt.setVisibility(View.GONE);
            }
            */

            if(arg0==mListViews.size()-1)
            {
                //UtilToast.show("sdsd_"+arg0);
                ImageView view = (ImageView) mListViews.get(arg0);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToMainActivity();

                    }
                });
            }

        }

    }



    @Override
    public void onClick(View v) {
         Intent intent = null;
         intent = new Intent(SplashActivity.this, MainActivity.class);
         startActivity(intent);
         finish();

        switch (v.getId()) {

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
