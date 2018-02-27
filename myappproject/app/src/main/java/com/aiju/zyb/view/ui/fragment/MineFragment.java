package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.view.ui.SetingActivity;
import com.aiju.zyb.view.widget.BannerGroup;
import com.aiju.zyb.view.widget.BannerItem;
import com.aiju.zyb.view.widget.BannerMenuChart;
/*
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
*/

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {
    private View view;
    private LinearLayout my_content;
    private ImageView head_img;
    private TextView login;
    private TextView login_name;
    public MineFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return view;
    }


    /**
     *
     * 控件初始化
     */
    private void initView()
    {
          my_content=(LinearLayout)view.findViewById(R.id.my_content);
          head_img=(ImageView)view.findViewById(R.id.head_img);
          login=(TextView)view.findViewById(R.id.login);
          login_name=(TextView)view.findViewById(R.id.login_name);
          login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

         initData();
         commonView(my_content);
    }


    /**
     *
     *
     */
    private void initData()
    {
        /*
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        Session session=AlibcLogin.getInstance().getSession();
        if(alibcLogin.isLogin()) {
            if (session != null) {
                login.setVisibility(View.GONE);
                login_name.setVisibility(View.VISIBLE);
                // GlideTool.glideImageLoad(getActivity(),session.avatarUrl,head_img,R.mipmap.ic_default);
                Glide.with(getActivity()).load(session.avatarUrl).placeholder(R.drawable.dsb_no_avatar).transform(new GlideCircleTransform(getActivity())).into(head_img);
                login_name.setText(session.nick);
            } else {
                head_img.setImageResource(R.drawable.dsb_no_avatar);
                login.setVisibility(View.VISIBLE);
                login_name.setVisibility(View.GONE);
            }
        }else{
            head_img.setImageResource(R.drawable.dsb_no_avatar);
            login.setVisibility(View.VISIBLE);
            login_name.setVisibility(View.GONE);
        }
        */
    }

    /***
     *
     *非管理员
     */
    private  void  commonView(ViewGroup parentView)
    {
       parentView.removeAllViews();
        List<BannerItem> takeItemsSet = new ArrayList<>();
        takeItemsSet.add(new BannerItem(R.mipmap.chart_7, "订单", R.mipmap.turn_right_chart));
        final BannerGroup takeGroupSet = new BannerGroup("", takeItemsSet);
        BannerMenuChart takeBannerSet = new BannerMenuChart(getActivity(), takeGroupSet, new BannerMenuChart.onBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                switch (position) {
                    case 0:
                         jumpActivity(0);
                        break;
                }
            }
        });


        parentView.addView(takeBannerSet);


        List<BannerItem> takeItemsMore = new ArrayList<>();
        takeItemsMore.add(new BannerItem(R.mipmap.invite_friend_imgs, "设置", R.mipmap.turn_right_chart));
      //  takeItemsMore.add(new BannerItem(R.mipmap.new_about_us, "关于我们", R.mipmap.turn_right_chart));
       // takeItemsMore.add(new BannerItem(R.mipmap.version_tip_imgs, "版本说明", R.mipmap.turn_right_chart));
        final BannerGroup takeGroupMore = new BannerGroup("", takeItemsMore);
        BannerMenuChart takeBannerMore = new BannerMenuChart(getActivity(), takeGroupMore, new BannerMenuChart.onBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                switch (position) {
                    case 0:
                         jumpActivity(1);
                        break;
                    case 1:
                      //  jumpActivity(9);
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
            case 0:  //订单
              // intent.setClass(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.tarnslate_int_from_right, R.anim.push_out);
                break;
            case 1: //设置
                intent.setClass(getActivity(), SetingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.tarnslate_int_from_right, R.anim.push_out);
                break;

        }

    }


    /**
     * 登录
     */
    public void login(View view) {
        //LoadingDialogTools.showWaittingDialog(getActivity());
        /*
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(getActivity(), "登录成功 ", Toast.LENGTH_LONG).show();
              //  nick = jiangyang_taobao, ava = https://wwc.alicdn.com/avatar/getAvatar.do?userId=1108967329&width=160&height=160&type=sns , openId=AAEK-yEcAEMYuIZ3wYGKBDE3, openSid=f6e7644764e3cac53e0dae5841b1e5f84e38c6703a19d28ec6f5e10e9f40569ca2c1aadeaad36e20c7f1176918176daa
                 // HLog.w("taoke", "获取淘宝用户信息: "+AlibcLogin.getInstance().getSession());
                initData();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getActivity(), "登录失败 ", Toast.LENGTH_LONG).show();
            }
        });
        */
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //登录须重写onActivityResult方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  CallbackContext.onActivityResult(requestCode, resultCode, data);
    }
}
