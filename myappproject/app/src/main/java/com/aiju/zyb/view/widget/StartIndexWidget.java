package com.aiju.zyb.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.my.baselibrary.utils.GlideTool;
import com.my.baselibrary.utils.SettingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AIJU on 2017-05-01.
 */

public class StartIndexWidget extends LinearLayout {
    public StartIndexWidget(Context context) {
        super(context);
        mContext = context;
        initWeight();
    }

    public StartIndexWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initWeight();

    }

    public StartIndexWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initWeight();
    }

    private Context mContext;
    private double scale = 2.13;
    private ImageView _iv;
    private List<Bitmap> listAd = new ArrayList<Bitmap>();
    private List<TaokeItemBean> list_Ad = new ArrayList<TaokeItemBean>();
    private int defaultImage = R.mipmap.ic_default;
    private int listCount = 0;
    private boolean flag = false;

    /**
     * @param defaultImage
     *            the defaultImage to set
     */
    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
        // viewLayout.setBackgroundDrawable(getContext().getResources().getDrawable(defaultImage));

    }

    /**
     * 轮播广告 宽/高 的比例
     * */
    public void setScale(double scale) {
        this.scale = scale;
        try {
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) vp.getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams.height = (int) (((double) SettingUtil.getDisplaywidthPixels()) / scale);
            vp.setLayoutParams(linearParams);

            RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) viewLayout.getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams2.height = (int) (((double) SettingUtil.getDisplaywidthPixels()) / scale);
            viewLayout.setLayoutParams(linearParams2);

        } catch (Exception e) {
        }
    }

    public void setListAd(List<TaokeItemBean> list_Ad) {
        this.list_Ad = list_Ad;
        this.listCount = list_Ad.size();
        creatView();
    }

    private DisallowParentTouchViewPager vp;
    private ArrayList<View> listView = new ArrayList<View>();
    private LinearLayout IndicatorLayout;
    private RelativeLayout startin;
    private int type = 0;

    public LinearLayout.LayoutParams pageLineLayoutParams = null;
    View viewLayout = null;

    private void initWeight() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_advertisement_layout, null);
        viewLayout = view.findViewById(R.id.viewLayout);
        vp = (DisallowParentTouchViewPager) view.findViewById(R.id.vp);
        IndicatorLayout = (LinearLayout) view.findViewById(R.id.IndicatorLayout);
        startin = (RelativeLayout) view.findViewById(R.id.startin);
        startin.setVisibility(View.GONE);

        pageLineLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                makesurePosition();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        creatView();

        touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();

        this.addView(view);

        try {
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) vp.getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams.height = (int) (((double) SettingUtil.getDisplaywidthPixels()) / scale);
            vp.setLayoutParams(linearParams);

            RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) viewLayout.getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams2.height = (int) (((double) SettingUtil.getDisplaywidthPixels()) / scale);
            viewLayout.setLayoutParams(linearParams2);

        } catch (Exception e) {

        }
    }

    int touchSlop = 0;


    public void setNestParent(ViewGroup parent) {
       vp.setNestParent(parent);
    }

    public void creatView() {
        try {

            listView.clear();
            for (int i = 0; i < listCount; i++) {
                View views = LayoutInflater.from(mContext).inflate(R.layout.viewpager_adverisement_layout_item, null);
                listView.add(views);
            }
            vp.setAdapter(new mypageAdapter());
            if (listCount > 1) {
                creatIndex();
            }
            vp.setOnTouchListener(touchListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建导航点.
     */
    public void creatIndex() {
        try {
            // 显示下面的点
            IndicatorLayout.removeAllViews();
            IndicatorLayout.setGravity(Gravity.CENTER);
            for (int j = 0; j < listView.size(); j++) {
                ImageView imageView = new ImageView(mContext);
                pageLineLayoutParams.setMargins(5, 5, 5, 5);
                imageView.setLayoutParams(pageLineLayoutParams);
                if (j == 0) {
                    imageView.setImageResource(R.mipmap.img_point_focused);
                } else {
                    imageView.setImageResource(R.mipmap.img_point_nomal);
                }
                IndicatorLayout.addView(imageView, j);
            }

        } catch (Exception e) {
        } catch (OutOfMemoryError e) {
        } catch (Error e) {
        }
    }

    int position = 0;

    /**
     * 定位点的位置.
     */
    public void makesurePosition() {
        position = vp.getCurrentItem();
        for (int j = 0; j < listView.size(); j++) {
            if (position == j) {
                ((ImageView) IndicatorLayout.getChildAt(position)).setImageResource(R.mipmap.img_point_focused);
            } else {
                ((ImageView) IndicatorLayout.getChildAt(j)).setImageResource(R.mipmap.img_point_nomal);
            }

            if (position == listView.size() - 1) {
               // if (shareListener != null) {
                    //shareListener.StartCallBack(position, type);
              //  }
            }

        }

    }

    class mypageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return listView.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager) v).addView(listView.get(position));
            _iv = (ImageView) listView.get(position).findViewById(R.id.ivBigPricture);
            try {

                GlideTool.glideImageLoad(mContext,list_Ad.get(position).getPict_url(),_iv,R.color.white);
                /*
                if (listCount < 2) {
                    if (shareListener != null) {
                        shareListener.StartCallBack(position, type);
                    }
                }
                */

            } catch (Exception e) {
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
                System.exit(0);
            } catch (Error e) {
            }
            return listView.get(position);
        }

        @Override
        public void destroyItem(View v, int position, Object arg2) {
            ((ViewPager) v).removeView(listView.get(position));
        }
    }

    private CallBackViewPagerOnclickListener viewPagerOnclickListener = null;

    public void setCallBack(CallBackViewPagerOnclickListener callBack) {
        viewPagerOnclickListener = callBack;
    }

    public interface CallBackViewPagerOnclickListener {
        public void callback(int position, int type, boolean flag);
    }

    float downX = 0;
    private OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                downX = event.getX();
                stopPlay();
            } else if (MotionEvent.ACTION_UP == event.getAction()) {
                startPlay();
                float lastX = event.getX();
                float lastDistance = Math.abs(lastX - downX);
                // 当是触摸viewPager时，不是滑动（滑动不做任何操作）
                if (lastDistance < 10) {
                    if (viewPagerOnclickListener != null)
                        viewPagerOnclickListener.callback(position, type, flag);
                }
            }
            return false;
        }
    };


    boolean play = false;

    /** 用与轮换的 handler. */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                try {
                    if(list_Ad.size()<2){
                        return;
                    }

                    /*
                    int i=vp.getCurrentItem();

                    if(i==0 || i>=list_Ad.size()*100-1){
                        i=list_Ad.size()*100/2;
                    }else
                    {
                        i++;
                    }
                    vp.setCurrentItem(i,true);// 取消动画
                    */



				int count =list_Ad.size();
				int i = vp.getCurrentItem();
				if (count < 2)
					return;

				if (i + 1 >= count) {
					i = 0;
				} else {
					i++;
				}

				vp.setCurrentItem(i, false);

                } catch (Exception e) {
                }
                if (play) {
                    handler.postDelayed(runnable, 3000);
                }
            }
        }
    };
    /** 用于轮播的线程. */
    private Runnable runnable = new Runnable() {
        public void run() {
            if (vp != null) {
                handler.sendEmptyMessage(0);
            }
        }
    };

    /**
     * 描述：自动轮播.
     */
    public void startPlay() {
        if (handler != null) {
            play = true;
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 3000);
        }
    }

    /**
     * 描述：自动轮播.
     */
    public void stopPlay() {
        if (handler != null) {
            play = false;
            handler.removeCallbacks(runnable);
        }
    }

}

