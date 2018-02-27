package com.aiju.zyb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiju.zyb.manage.VersionManager;
import com.aiju.zyb.view.BaseFragmentActivity;
import com.aiju.zyb.view.ui.fragment.BrandFragment;
import com.aiju.zyb.view.ui.fragment.IndexFragment;
import com.aiju.zyb.view.ui.fragment.JobIndexFragment;
import com.aiju.zyb.view.ui.fragment.JobPlanFragment;
import com.aiju.zyb.view.ui.fragment.MessageFragment;
import com.aiju.zyb.view.ui.fragment.MineFragment;
import com.aiju.zyb.view.ui.fragment.MyFragment;
import com.aiju.zyb.view.ui.fragment.ShopCartFragment;
import com.aiju.zyb.view.ui.fragment.SortFragment;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener{
    public enum Page {
        page_home,
        page_work_home,
        page_contacts,
        page_user
    }

    private  Page curPage = Page.page_home;
    private FrameLayout mContentLayout;
    private Fragment mCurrentFragment;
    private LinearLayout mHomeLayout, mStoreLayout, mFiguresLayout, mUserLayout,home_tab_job_layout;
    private TextView unread_msg;
    private LinearLayout mBottomLayout;

    private JobIndexFragment indexFragment;  //首页
    private BrandFragment brandFragment;
    private ShopCartFragment shopCartFragment;
   // private MineFragment myFragment;
    private SortFragment sortFragment;
    private MessageFragment messageFragment; //消息
    private JobPlanFragment jobPlanFragment;//职业说
    private MyFragment myFragment; //我


    @Override
    protected void initData() {
        //VersionManager  version=new VersionManager(this);
       // version.initiate(0,null);

    }


    /**
     * 点击tab键
     *
     * @param page
     * @return
     */
    public boolean setClickLayout(Page page) {
        if (page == curPage)
            return false;
        disableLayout(curPage);
        enableLayout(page);
        curPage = page;
        return true;
    }

    /**
     * 使layout处于选中状态
     */
    public void enableLayout(Page page) {
        LinearLayout layout = getLayout(page);
        layout.setActivated(true);
    }

    /**
     * 使layout处于未选中状态
     */
    public void disableLayout(Page page) {
        LinearLayout layout = getLayout(page);
        layout.setActivated(false);
    }

    /**
     * 将page与layout相连
     *
     * @param page
     * @return
     */
    public LinearLayout getLayout(Page page) {
        LinearLayout view = mHomeLayout;
        switch (page) {
            case page_home:
                view = mHomeLayout;
                break;
            case page_work_home:
                view = mStoreLayout;
                break;
            case page_contacts:
                view = mFiguresLayout;
                break;
            case page_user:
                view = mUserLayout;
                break;
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_tab_home_layout: //首页
                if (setClickLayout(Page.page_home)) {
                    changeFragment(Page.page_home);
                }
                break;
            case R.id.home_tab_work_home_layout: //职业说
                if (setClickLayout(Page.page_work_home)) {
                    changeFragment(Page.page_work_home);
                }
                break;
            case R.id.home_tab_contacts_layout:  //  消息
                if (setClickLayout(Page.page_contacts))
                    changeFragment(Page.page_contacts);
                break;
            case R.id.home_tab_me_layout: // 我
                if (setClickLayout(Page.page_user)) {
                    changeFragment(Page.page_user);
                }
                break;
            case R.id.home_tab_job_layout:

                break;
        }
    }


    public void changeFragment(Page page) {
        switch (page) {
            case page_home:  //首页
                switchChildFragment(R.id.home_main_tab_content,indexFragment, false);
                break;
            case page_work_home:  //职业说
                switchChildFragment(R.id.home_main_tab_content, jobPlanFragment, false);
                break;
            case page_contacts:  //消息
                switchChildFragment(R.id.home_main_tab_content, messageFragment, false);
                break;
            case page_user:  //我
                switchChildFragment(R.id.home_main_tab_content, myFragment, false);
                break;
        }
    }

    public void switchChildFragment(int resId, Fragment childFragment, boolean isBills) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!childFragment.isAdded()) {
            transaction
                    .hide(mCurrentFragment)
                    .add(resId, childFragment)
                    .commit();
        } else {
            transaction
                    .hide(mCurrentFragment)
                    .show(childFragment)
                    .commit();
        }
        mCurrentFragment = childFragment;

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initView() {
        mContentLayout = (FrameLayout) findViewById(R.id.home_main_tab_content);
        indexFragment= JobIndexFragment.newInstance();
      //  brandFragment= BrandFragment.newInstance();
       // shopCartFragment= ShopCartFragment.newInstance();
        myFragment= MyFragment.newInstance();//MineFragment.newInstance();
      //  sortFragment= SortFragment.newInstance();

        messageFragment=MessageFragment.newInstance();
        jobPlanFragment=JobPlanFragment.newInstance();

        mHomeLayout = (LinearLayout) findViewById(R.id.home_tab_home_layout);  //首页
        mHomeLayout.setOnClickListener(this);

        mStoreLayout = (LinearLayout) findViewById(R.id.home_tab_work_home_layout);  //职业说
        mStoreLayout.setOnClickListener(this);

        mFiguresLayout = (LinearLayout) findViewById(R.id.home_tab_contacts_layout);  //消息
        mFiguresLayout.setOnClickListener(this);

        mUserLayout = (LinearLayout) findViewById(R.id.home_tab_me_layout);   //我
        mUserLayout.setOnClickListener(this);

        home_tab_job_layout=(LinearLayout)findViewById(R.id.home_tab_job_layout); //发布
        home_tab_job_layout.setOnClickListener(this);

        mBottomLayout = (LinearLayout) findViewById(R.id.index_tab);

        mCurrentFragment =indexFragment;
        // UtilToast.show(page);
        enableLayout(curPage);
        getSupportFragmentManager().beginTransaction().add(R.id.home_main_tab_content, mCurrentFragment).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //在按一次退出的时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
           // if(!shopCartFragment.goBack()) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
          //  }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
