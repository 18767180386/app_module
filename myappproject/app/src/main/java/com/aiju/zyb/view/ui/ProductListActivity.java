package com.aiju.zyb.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.presenter.TaokePresenter;
import com.aiju.zyb.util.Utils;
import com.aiju.zyb.view.BaseActivity;
import com.aiju.zyb.view.adapter.ProductListAdapter;
import com.aiju.zyb.view.viewholder.SpacesItemDecorations;
import com.aiju.zyb.view.widget.LoadMoreRecyclerView;
import com.aiju.zyb.view.widget.ProductNavLayout;
import com.google.gson.Gson;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.SettingUtil;

public class ProductListActivity extends BaseActivity implements View.OnClickListener,IProListView,ProductListAdapter.OnItemClickListener  {
    private ProductNavLayout pro_nav;
    private LinearLayout top_nav;
    private LinearLayout pro_li_id;
    private LinearLayout price_li;
    private TextView all_pro;
    private TextView price;
    private LoadMoreRecyclerView mRecyclerView;

    private int flag = 0;
    private int pageIndex = 1;
    private int pageSize = 20;
    private boolean isLoadMore=false;
    private int type=0;
    private TaokePresenter taokePresenter;
    private int mLastVisibleItemPosition = 0;
    private Boolean loadFlag=false;
    private GridLayoutManager mLayoutManager;
    private static final int SPAN_COUNT = 2;
    private ProductListAdapter indexChildAdapter;
    private int leftRight = SettingUtil.dip2px(10);
    private int topBottom = SettingUtil.dip2px(10);
    private String keyword="商品列表";
    private int sortType=0;  //0默认降序   1 升序   2 价格降序 3 价格升序

    @Override
    protected void initView() {
        taokePresenter=new TaokePresenter(this);
        pro_li_id=(LinearLayout)findViewById(R.id.pro_li_id);
        price_li=(LinearLayout)findViewById(R.id.price_li);
        all_pro=(TextView) findViewById(R.id.all_pro);
        all_pro.setOnClickListener(this);
        price=(TextView)findViewById(R.id.price);
        price.setOnClickListener(this);
        initTab();
        mRecyclerView=(LoadMoreRecyclerView)findViewById(R.id.order_recyclerview);
        Bundle bundle= getIntent().getExtras();
        if(bundle.containsKey("keyword")) {
            keyword = bundle.getString("keyword");
        }

        mLayoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
        indexChildAdapter=new ProductListAdapter(this);
        indexChildAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(indexChildAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecorations(leftRight, topBottom));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAutoLoadMoreEnable(true);
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
               // stockPresenter.getWareWaring(user.getVisit_id(), alert_type, entrepot_id, keyword, pageSize + "", page + "", way_amount_flag);
                loadData(false);
            }
        });
        loadData(true);
    }

    @Override
    public void onItemClick(View view, int position) {
        if(indexChildAdapter!=null)
        {
           showDetail(indexChildAdapter.mValues.get(position).getNum_iid());
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }



    @Override
    protected void initData() {


    }
    /**
     *
     * 加载数据
     */
    private  void loadData(boolean flag)
    {
        pageIndex=flag?1:pageIndex;
        loadFlag=flag;
        taokePresenter.proSearchList(this,keyword,sortType,pageIndex,pageSize);
    }



    @Override
    public void getProList(ResultPageList<TaokeItemBean> result)
    {
        HLog.w("ret",new Gson().toJson(result));
        if(result.getCode().equals("000"))
        {
            if(result.getData()!=null && result.getData().size()>0)
            {
                pageIndex++;
                HLog.w("page",pageIndex+"");
                isLoadMore=false;
                if(loadFlag) {
                    indexChildAdapter.setData(result.getData());
                    mRecyclerView.setAdapterNotify();
                    if (result.getData().size() >= pageSize) {
                        mRecyclerView.notifyMoreFinish(true);
                    } else {
                        mRecyclerView.notifyMoreFinish(false);
                    }
                }else{
                     indexChildAdapter.addDatas(result.getData());
                    if (result.getData().size() >= pageSize) {
                        mRecyclerView.notifyMoreFinish(true);
                    } else {
                        mRecyclerView.notifyMoreFinish(false);
                    }
                }
            }else{
                isLoadMore=true;
                if(loadFlag) {
                    mRecyclerView.notifyMoreFinish(false);
                }else{
                    mRecyclerView.notifyMoreFinish(false);
                }
            }
        }
    }


    @Override
    protected String getTextTitle() {
        return keyword;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.all_pro:
                 all_pro.setActivated(true);
                 price.setActivated(false);
                 Utils.setDrable(R.drawable.price_no_low_top,price,1);
                 sortType=0;
                 loadData(true);
                 break;
             case R.id.price:
                 all_pro.setActivated(false);
                 price.setActivated(true);
                 if(sortType==3)
                 {
                     sortType=2;
                     Utils.setDrable(R.drawable.price_low_top,price,1);
                 }else if(sortType==2)
                 {
                     sortType=3;
                     Utils.setDrable(R.drawable.price_top_low,price,1);
                 }else {
                    sortType=3;
                     Utils.setDrable(R.drawable.price_top_low,price,1);
                 }
                 loadData(true);
                 break;
         }
    }


    /**
     *
     * 初始化tab
     */
    private  void initTab()
    {
        all_pro.setActivated(true);
        price.setActivated(false);
        Utils.setDrable(R.drawable.price_no_low_top,price,1);
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
        //Intent intent=new Intent(this,ProductDetailActivity.class);
      //  intent.putExtra("taokeid",itemId);
      //  intent.putExtra("istaoke",true);
      //  startActivity(intent);
        // startActivity(new Intent(getActivity(), ProductDetailActivity.class));
        // AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , new DemoTradeCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
