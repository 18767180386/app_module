package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeIndexAdBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.presenter.TaokePresenter;
import com.aiju.zyb.view.BaseFragment;
import com.aiju.zyb.view.adapter.MyViewPagerAdapter;
import com.aiju.zyb.view.ui.ITaokeView;
import com.aiju.zyb.view.ui.SearchActivity;
import com.aiju.zyb.view.widget.StartIndexWidget;
import com.aiju.zyb.view.widget.StickyNavLayout;
//import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
//import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
//import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.my.baselibrary.utils.GlideTool;
import com.my.baselibrary.utils.SettingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class IndexFragment extends BaseFragment implements ITaokeView,ViewPager.OnPageChangeListener,StickyNavLayout.OnScrollY{
    private TextView  test_btn;
    public  TaokePresenter taokePresenter;
    private TabLayout id_tablayout;
    private ViewPager id_viewpager;
    private StartIndexWidget index_ad_view;
    private PtrClassicFrameLayout message_attence_ptr_pull_refresh;
    // TabLayout中的tab标题
    private List<TaokeProSort> mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;

    private StickyNavLayout sticky_view;
    private TranslateAnimation showView;
    private TranslateAnimation hiddenView;
    private View header;
    private RelativeLayout re_scroll_top;
    private RelativeLayout header_re;
    private TextView txt_search_index;

    /**
     * 悬浮菜单距离顶部的高度，这里与顶部title的高度一致
     */
    private int headHeight = 0;

    /**
     * 底部菜单按钮的高度
     */
    private int bottomMenuHeight = 0;

    private double scale=1.8;

    private LinearLayout index_pro_sec_ad;

    private ImageView pro_one,pro_two,pro_three;

    private List<TaokeItemBean> cauList=null;
    public IndexFragment() {
        // Required empty public constructor
    }


    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }



    @Override
    protected   void  initView(View view)
    {

       // alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
       // exParams = new HashMap<>();
      //  exParams.put("isv_code", "appisvcode");
      //  exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        headHeight = (int) getResources().getDimension(R.dimen.header_height_65);
        bottomMenuHeight = (int) getResources().getDimension(R.dimen.bottom_menu_height_50);
        isShowTitleBar(false);
        //   0上新	 1 女装  2居家  3 母婴 4，鞋包配饰  5 美妆  6 美食   7  数码  8男士  9 保健
        // Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写


        //显示动画
        showView = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showView.setDuration(500);
        //隐藏动画
        hiddenView = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        hiddenView.setDuration(500);

        header = view.findViewById(R.id.header);
       // header.setBackgroundColor(Color.argb(0, 255, 85, 66));//AGB由相关工具获得，或者美工提供
        header.setBackgroundColor(Color.argb(0, 255, 255, 255));

      //  re_scroll_top = (RelativeLayout) view.findViewById(R.id.re_scroll_top);
        taokePresenter=new TaokePresenter(this);
        id_tablayout=(TabLayout)view.findViewById(R.id.id_tablayout);
        id_viewpager=(ViewPager)view.findViewById(R.id.id_viewpager);
        index_ad_view=(StartIndexWidget)view.findViewById(R.id.index_ad_view);
        index_ad_view.setScale(scale);
        index_ad_view.getLayoutParams().height=(int) (((double) SettingUtil.getDisplaywidthPixels()) / scale);
        index_ad_view.setCallBack(new StartIndexWidget.CallBackViewPagerOnclickListener(){
            @Override
            public void callback(int position, int type, boolean flag)
            {
                showDetail(cauList.get(position).getNum_iid());
            }
        });

        txt_search_index=(TextView) view.findViewById(R.id.txt_search_index);
        txt_search_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        sticky_view=(StickyNavLayout)view.findViewById(R.id.sticky_view);
        sticky_view.setBottomMenuHeight(bottomMenuHeight);
        sticky_view.setHeadHeight(headHeight);
        sticky_view.setOnScrollY(this);
        message_attence_ptr_pull_refresh=(PtrClassicFrameLayout)view.findViewById(R.id.message_attence_ptr_pull_refresh);

        message_attence_ptr_pull_refresh.setMode(PtrFrameLayout.Mode.REFRESH);
        message_attence_ptr_pull_refresh.setLastUpdateTimeRelateObject(this);

        message_attence_ptr_pull_refresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
             //  return    !canChildScrollUp(content);
               // return !(session_list.getChildCount() > 0 && (session_list.getFirstVisiblePosition() > 0 || session_list.getChildAt(0).getTop() < session_list.getPaddingTop()));
                return sticky_view.getCurScrolly()<=0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                taokePresenter.getIndexAdList(getActivity(),1);
            }
        });

        message_attence_ptr_pull_refresh.setOnTouchScrollY(new PtrFrameLayout.OnTouchScrollY() {
            int scrollY = 0;

            @Override
            public void onScrollY(float y) {
                //ToastUtil.setToast("3");
                if (scrollY < y + 100) {
                    //下拉
                    scrollY = (int) y;
                    //隐藏title
                    if (header.getVisibility() == View.VISIBLE) {
                        header.startAnimation(hiddenView);
                        header.setVisibility(View.INVISIBLE);
                    }
                } else {
                    //上拉
                    scrollY = (int) y;
                    //显示
                    if (header.getVisibility() == View.INVISIBLE) {
                        header.startAnimation(showView);
                        header.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onConplete() {
                //ToastUtil.setToast("1");
                if (header.getVisibility() == View.INVISIBLE) {
                    header.startAnimation(showView);
                    header.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScroll() {
                //释放自动弹回去
                //显示
               // ToastUtil.setToast("2");
                if (header.getVisibility() == View.INVISIBLE) {
                    header.startAnimation(showView);
                    header.setVisibility(View.VISIBLE);
                }
            }
        });


        index_ad_view.setNestParent(message_attence_ptr_pull_refresh);

        // 初始化ViewPager的适配器，并设置给它


        id_viewpager.addOnPageChangeListener(this);
        id_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        index_pro_sec_ad=(LinearLayout)view.findViewById(R.id.index_pro_sec_ad);
        index_pro_sec_ad.getLayoutParams().height=SettingUtil.getDisplaywidthPixels()/2;
        pro_one=(ImageView)view.findViewById(R.id.pro_one);
        pro_two=(ImageView)view.findViewById(R.id.pro_two);
        pro_three=(ImageView)view.findViewById(R.id.pro_three);
        taokePresenter.getIndexAdList(getActivity(),1);
        taokePresenter.getProSortList(getActivity());
    }


    /**
     *
     * 填充数据
     */
    private  void  fillData(List<TaokeProSort>  data)
    {
       // mTitles = getResources().getStringArray(R.array.tab_titles);
        mTitles=data;
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt(IndexChildFragment.ARG_PARAM, mTitles.get(i).getSortType());
            IndexChildFragment mFragment = new IndexChildFragment();
            mFragment.setArguments(mBundle);
            mFragments.add(i, mFragment);
        }

       mViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager(), mTitles, mFragments);
       id_viewpager.setAdapter(mViewPagerAdapter);

        // 将TabLayout和ViewPager进行关联，让两者联动起来
        id_tablayout.setupWithViewPager(id_viewpager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        id_tablayout.setTabsFromPagerAdapter(mViewPagerAdapter);
    }




    @Override
    public void scrollY(int y, int totalY) {
        //正在滚动
        if (y <= 0) {
           // header.setBackgroundColor(Color.argb(0, 255, 85, 66));//AGB由相关工具获得，或者美工提供
            header.setBackgroundColor(Color.argb(0, 255, 255, 255));
            message_attence_ptr_pull_refresh.setEnabled(true);
        } else if (y > 0) {
            float scale = (float) y / totalY;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
          //  header.setBackgroundColor(Color.argb((int) alpha, 255, 85, 66));
            header.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            message_attence_ptr_pull_refresh.setEnabled(false);

           // re_scroll_top.setVisibility(View.GONE);
        } else {
           // header.setBackgroundColor(Color.argb(255, 255, 85, 66));
            header.setBackgroundColor(Color.argb(0, 255, 255, 255));
            message_attence_ptr_pull_refresh.setEnabled(false);
        }
    }

    @Override
    public void scrollComplete() {
        //滚动完成
       // header.setBackgroundColor(Color.argb(255, 255, 85, 66));//AGB由相关工具获得，或者美工提供
        header.setBackgroundColor(Color.argb(255, 255, 255, 255));
        message_attence_ptr_pull_refresh.setEnabled(false);
        //re_scroll_top.setVisibility(View.VISIBLE);
    }

    @Override
    protected   void  initListener()
    {

    }

    @Override
    protected  void initData()
    {

    }

    @Override
    protected String getTextTitle() {
        return "首页";
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override public void onPageSelected(int position) {
        //mToolbar.setTitle(mTitles[position]);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override public void onPageScrollStateChanged(int state) {

    }


    @Override
    public   void getIndextList(ResultPageList<TaokeItemBean> result)
    {

    }

    @Override
   public   void getIndexAdList(Result<TaokeIndexAdBean> reuslt)
   {
    if(reuslt.getCode().equals("000"))
    {
        message_attence_ptr_pull_refresh.refreshComplete();
        final List<TaokeItemBean> list=reuslt.getData().getIndexsecondList();
        if(reuslt.getData()!=null)
        {
            for (int i=0;i<list.size();i++)
            {
                if(i==0)
                {
                    GlideTool.glideImageLoad(mContext,list.get(i).getPict_url(),pro_one,R.color.white);
                    pro_one.setTag(list.get(i).getNum_iid());
                    pro_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tag=v.getTag().toString();
                            showDetail(tag);
                        }
                    });
                }else if(i==1)
                {
                    GlideTool.glideImageLoad(mContext,list.get(i).getPict_url(),pro_two,R.color.white);
                    pro_two.setTag(list.get(i).getNum_iid());
                    pro_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tag=v.getTag().toString();
                            showDetail(tag);
                        }
                    });
                }else if(i==2)
                {
                    GlideTool.glideImageLoad(mContext,list.get(i).getPict_url(),pro_three,R.color.white);
                    pro_three.setTag(list.get(i).getNum_iid());
                    pro_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tag=v.getTag().toString();
                            showDetail(tag);
                        }
                    });
                }
            }
            cauList=reuslt.getData().getCarousellsit();
            index_ad_view.setListAd(reuslt.getData().getCarousellsit());
            index_ad_view.startPlay();
        }
    }
   }

 //   private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
 //   private AlibcTaokeParams alibcTaokeParams = null;//淘客参数，包括pid，unionid，subPid
    private Boolean isTaoke = false;//是否是淘客商品类型
    private String itemId = "522166121586";//默认商品id
    private String shopId = "60552065";//默认店铺id
    private Map<String, String> exParams;//yhhpass参数


    @Override
    public void getProSortList(ResultPageList<TaokeProSort> result)
    {
        if(result.getCode().equals("000"))
        {
            if(result.getData()!=null && result.getData().size()>0)
            {
                List<TaokeProSort> list=result.getData();
                if(list!=null && list.size()>0) {
                    fillData(list);
                }
            }
        }

    }


    /**
     *
     * 商品详情
     * @param itemId
     */
    public void showDetail(String  itemId) {

        /*
        AlibcBasePage alibcBasePage=null;
        alibcBasePage = new AlibcDetailPage(itemId);
        if (isTaoke) {
            alibcTaokeParams = new AlibcTaokeParams("mm_26632322_6858406_23810104", "", "");
        } else {
            alibcTaokeParams = null;
        }
        */
     //   Intent intent=new Intent(getActivity(),ProductDetailActivity.class);
      //  intent.putExtra("taokeid",itemId);
      //  intent.putExtra("istaoke",true);
       // startActivity(intent);
       // startActivity(new Intent(getActivity(), ProductDetailActivity.class));
       // AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , new DemoTradeCallback());
    }

    @Override
     public void onDestroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
     //   AlibcTradeSDK.destory();
        super.onDestroy();
    }

}
