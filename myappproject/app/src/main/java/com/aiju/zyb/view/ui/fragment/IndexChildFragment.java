package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeIndexAdBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.presenter.TaokePresenter;
import com.aiju.zyb.view.adapter.ProductListAdapter;
import com.aiju.zyb.view.ui.ITaokeView;
import com.aiju.zyb.view.viewholder.SpacesItemDecorations;
import com.aiju.zyb.view.widget.LoadMoreRecyclerView;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.SettingUtil;


public class IndexChildFragment extends Fragment implements ITaokeView,ProductListAdapter.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM = "param";
    private View mView;

    private LoadMoreRecyclerView mRecyclerView;
   // private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mLayoutManager;
  //  private RecyclerView.LayoutManager mLayoutManager;
  //  private MyRecyclerViewAdapter mRecyclerViewAdapter;
   // private MyStaggeredViewAdapter mStaggeredAdapter;
    private ProductListAdapter indexChildAdapter;

    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;

    private static final int SPAN_COUNT = 2;
    private int flag = 0;

    private int pageIndex = 1;
    private int pageSize = 20;
    private boolean isLoadMore=false;
    private int type=0;
    private TaokePresenter taokePresenter;
    private int mLastVisibleItemPosition = 0;
    private Boolean loadFlag=false;
    private    int leftRight = SettingUtil.dip2px(10);
    private int topBottom = SettingUtil.dip2px(10);
    public IndexChildFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IndexChildFragment newInstance(int param) {
        IndexChildFragment fragment = new IndexChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_index_child, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mView);
    }


    protected   void  initView(View view) {
        mRecyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.id_recyclerview);
        flag =2;// (int) getArguments().get("flag");
        type=getArguments().getInt(ARG_PARAM);
        configRecyclerView();
    }


    private void configRecyclerView() {
       // mLayoutManager = new LinearLayoutManager(getActivity());

        switch (flag) {
            case VERTICAL_LIST:
               // mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
               // mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
               // mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }


        indexChildAdapter=new ProductListAdapter(getActivity());
        indexChildAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(indexChildAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int spanCount = 2;
        int spacing = 5;
        boolean includeEdge = false;

        mRecyclerView.addItemDecoration(new SpacesItemDecorations(leftRight, topBottom));
      //  mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
      // mRecyclerView.addItemDecoration(new SpaceItemDecoration(SettingUtil.dp2px(10)));
       // mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAutoLoadMoreEnable(true);
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // stockPresenter.getWareWaring(user.getVisit_id(), alert_type, entrepot_id, keyword, pageSize + "", page + "", way_amount_flag);
                initData(false);
            }
        });
        taokePresenter=new TaokePresenter(this);
        initData(true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     *
     * 加载数据
     */
    private  void initData(boolean flag)
    {
        pageIndex=flag?1:pageIndex;
        loadFlag=flag;
        HLog.w("page",type+"-----"+loadFlag);
        taokePresenter.getIndexList(getActivity(),type,pageIndex,pageSize);
    }





    @Override
    public   void getIndextList(ResultPageList<TaokeItemBean> result)
    {
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
    public   void getIndexAdList(Result<TaokeIndexAdBean> reuslt)
    {

    }
    @Override
    public void getProSortList(ResultPageList<TaokeProSort> result)
    {


    }


    @Override
    public void onItemClick(View view, int position) {
        //SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
        if(indexChildAdapter!=null)
        {
            showDetail(indexChildAdapter.mValues.get(position).getNum_iid());
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
       // SnackbarUtil.show(mRecyclerView, getString(R.string.item_longclicked), 0);
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
        //Intent intent=new Intent(getActivity(),ProductDetailActivity.class);
      //  intent.putExtra("taokeid",itemId);
      //  intent.putExtra("istaoke",true);
       // startActivity(intent);
        // startActivity(new Intent(getActivity(), ProductDetailActivity.class));
        // AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , new DemoTradeCallback());
    }

}
