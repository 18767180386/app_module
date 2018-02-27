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

public class MessageFragment extends BaseFragment {

    public MessageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
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
        return  this.getActivity().getResources().getString(R.string.ec_tab_contacts);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
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
