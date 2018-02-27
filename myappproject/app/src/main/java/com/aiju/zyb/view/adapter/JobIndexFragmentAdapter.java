package com.aiju.zyb.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aiju.zyb.bean.OccupationTypeBean;
import com.aiju.zyb.bean.TaokeProSort;

import java.util.List;

/**
 * Created by john on 2018/2/5.
 */

public class JobIndexFragmentAdapter extends FragmentStatePagerAdapter {

    private List<OccupationTypeBean> mTitles;
    private List<Fragment> mFragments;

    public JobIndexFragmentAdapter(FragmentManager fm, List<OccupationTypeBean> mTitles, List<Fragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override public CharSequence getPageTitle(int position) {
        return mTitles.get(position).getSortName();
    }

    @Override public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override public int getCount() {
        return mFragments.size();
    }
}