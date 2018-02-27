package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.view.View;

import com.aiju.zyb.view.BaseFragment;
import com.aiju.zyb.R;

public class BrandFragment extends BaseFragment {

    public BrandFragment() {
        // Required empty public constructor
    }


    public static BrandFragment newInstance() {
        BrandFragment fragment = new BrandFragment();
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
        return "品牌";
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_brand;
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
