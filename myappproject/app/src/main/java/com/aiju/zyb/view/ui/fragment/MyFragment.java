package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.view.View;

import com.aiju.zyb.R;
import com.aiju.zyb.view.BaseFragment;

public class MyFragment extends BaseFragment {
    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected   void  initView(View view)
    {

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
        return "我的";
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
