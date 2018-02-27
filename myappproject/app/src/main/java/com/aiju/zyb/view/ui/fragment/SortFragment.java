package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.presenter.TaokePresenter;
import com.aiju.zyb.view.BaseFragment;
import com.aiju.zyb.view.ui.ISortView;
import com.aiju.zyb.view.ui.ProductListActivity;
import com.my.baselibrary.utils.HLog;

import java.util.List;

public class SortFragment extends BaseFragment implements ISortView {
    private  View view;
    private List<TaokeProSort> toolsList;
    private TextView toolsTextViews[];
    private View views[];
    private LayoutInflater inflater;
    private ScrollView scrollView;
    private int scrllViewWidth = 0, scrollViewMiddle = 0;
    private ViewPager shop_pager;
    private int currentItem = 0;
    private ShopAdapter shopAdapter;
    private TaokePresenter taokePresenter;

    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        return fragment;
    }

    @Override
    protected   void  initView(View view)
    {
        showHead(1);
        taokePresenter=new TaokePresenter(this);
        this.view=view;
        scrollView = (ScrollView)view.findViewById(R.id.tools_scrlllview);
        inflater = LayoutInflater.from(getActivity());
        taokePresenter.getProSortList(getActivity());
    }


    /**
     * 动态生成显示items中的textview
     */
    private void showToolsView(List<TaokeProSort> list) {
      //  toolsList = new String[]{"常用分类", "潮流女装", "品牌男装", "内衣配饰", "家用电器", "手机数码", "电脑办公", "个护化妆", "母婴频道", "食物生鲜", "酒水饮料", "家居家纺", "整车车品", "鞋靴箱包", "运动户外", "图书", "玩具乐器", "钟表", "居家生活", "珠宝饰品", "音像制品", "家具建材", "计生情趣", "营养保健", "奢侈礼品", "生活服务", "旅游出行"};
        toolsList =list;// new String[]{"常用分类", "潮流女装", "品牌男装", "内衣配饰", "家用电器"};
        shopAdapter = new ShopAdapter(getChildFragmentManager());
        initPager();

        LinearLayout toolsLayout = (LinearLayout)view.findViewById(R.id.tools);
        toolsTextViews = new TextView[toolsList.size()];
        views = new View[toolsList.size()];
        for (int i = 0; i < toolsList.size(); i++) {
            View view = inflater.inflate(R.layout.item_b_top_nav_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(toolsList.get(i).getSortName());
            toolsLayout.addView(view);
            toolsTextViews[i] = textView;
            views[i] = view;
        }
        changeTextColor(0);
    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shop_pager.setCurrentItem(v.getId());
        }
    };


    /**
     * initPager<br/>
     * 初始化ViewPager控件相关内容
     */
    private void initPager() {
        shop_pager = (ViewPager)view.findViewById(R.id.goods_pager);
        shop_pager.setAdapter(shopAdapter);
        shop_pager.setOnPageChangeListener(onPageChangeListener);
    }

    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (shop_pager.getCurrentItem() != arg0) shop_pager.setCurrentItem(arg0);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }
            currentItem = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    /**
     * ViewPager 加载选项卡
     *
     * @author Administrator
     */
    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = new Fragment_pro_type();
            Bundle bundle = new Bundle();
            String str = toolsList.get(arg0).getSortName();
            bundle.putString("typename", str);
            bundle.putInt("type",toolsList.get(arg0).getSortType());
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return toolsList.size();
        }
    }


    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setBackgroundResource(R.drawable.sortshape_default);
                toolsTextViews[i].setTextColor(getResources().getColor(R.color.color_33));
            }
        }
        toolsTextViews[id].setBackgroundResource(R.drawable.sortshape_select);
        toolsTextViews[id].setTextColor(getResources().getColor(R.color.ec_tab_press)); //0xffff5d5e
    }


    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        HLog.w("view_w",x+"------------");
        scrollView.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrllViewWidth == 0)
            scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
        return scrllViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }


    @Override
    public  void doSearch(String key) {
        HLog.w("ret",key);
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("keyword",key);
        intent.putExtras(bundle);
        intent.setClass(getActivity(),ProductListActivity.class);
        startActivity(intent);
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
        return "分类";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getProSortList(ResultPageList<TaokeProSort> result)
    {
        if(result.getCode().equals("000"))
        {
            if(result.getData()!=null && result.getData().size()>0)
            {
                List<TaokeProSort> list=result.getData();
                showToolsView(list);
            }
        }

    }

    @Override
    public void getProListByType(ResultPageList<TaokeItemBean> result)
    {

    }


}
