package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.OccupationBean;
import com.aiju.zyb.bean.OccupationTypeBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.presenter.OccupationPresenter;
import com.aiju.zyb.view.BaseFragment;
import com.aiju.zyb.view.adapter.JobIndexFragmentAdapter;
import com.aiju.zyb.view.adapter.MyViewPagerAdapter;
import com.aiju.zyb.view.ui.IOccupationView;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.my.baselibrary.utils.LoadingDialogTools;
import com.my.baselibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class JobIndexFragment extends BaseFragment implements IOccupationView{
    private TabLayout tablayout;
    private ViewPager view_pager;
    private OccupationPresenter occupationPresenter;
    private List<OccupationTypeBean> mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;

    private JobIndexFragmentAdapter jobIndexFragmentAdapter;
    public JobIndexFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static JobIndexFragment newInstance() {
        JobIndexFragment fragment = new JobIndexFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected   void  initView(View view)
    {
       // hideBack(1);
        showHead(2);
        occupationPresenter=new OccupationPresenter(this);
        tablayout = (TabLayout)view.findViewById(R.id.tablayout);
        // tablayout.setTabMode(TabLayout.MODE_FIXED);
        view_pager = (ViewPager)view.findViewById(R.id.view_pager);
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override

            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑

            }

        });
    }




    @Override
    protected   void  initListener()
    {

    }


    @Override
    protected  void initData()
    {
        LoadingDialogTools.showWaittingDialog(getActivity());
        occupationPresenter.getOccupationTypeList(mContext);
    }


    @Override
    public   void  getOccupationType(ResultPageList<OccupationTypeBean> result)
    {
        if(result.getStatus()==ResultPageList.Http_Success)
        {
            if(result.getData()!=null && result.getData().size()>0)
            {
                List<OccupationTypeBean> list=result.getData();
                mTitles=list;
                //初始化填充到ViewPager中的Fragment集合
                mFragments = new ArrayList<>();
                for (int i = 0; i < mTitles.size(); i++) {

                    Bundle mBundle = new Bundle();
                    mBundle.putString(JobIndexChildFragment.ARG_PARAM1, mTitles.get(i).getSortType()+"");
                    mBundle.putString(JobIndexChildFragment.ARG_PARAM2,"");
                    JobIndexChildFragment mFragment = new JobIndexChildFragment();
                    mFragment.setArguments(mBundle);
                    mFragments.add(i, mFragment);
                }
                jobIndexFragmentAdapter = new JobIndexFragmentAdapter(getChildFragmentManager(), mTitles, mFragments);
                view_pager.setAdapter(jobIndexFragmentAdapter);
                // 将TabLayout和ViewPager进行关联，让两者联动起来
                tablayout.setupWithViewPager(view_pager);
                // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
                tablayout.setTabsFromPagerAdapter(jobIndexFragmentAdapter);
            }
        }else{
            ToastUtil.setToast(result.getMsg());
        }
    }

    @Override
    public   void  getOccupationList(ResultPageList<OccupationBean> o)
    {

    }


    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    protected String getTextTitle() {
        return  this.getActivity().getResources().getString(R.string.ec_tab_home_title);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job_index;
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
