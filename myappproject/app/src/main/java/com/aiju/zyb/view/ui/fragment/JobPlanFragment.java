package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiju.zyb.R;
import com.aiju.zyb.view.BaseFragment;


public class JobPlanFragment extends BaseFragment {


    public JobPlanFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static JobPlanFragment newInstance() {
        JobPlanFragment fragment = new JobPlanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return  this.getActivity().getResources().getString(R.string.ec_tab_work_home);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job_plan;
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
