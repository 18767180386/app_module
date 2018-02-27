package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.OccupationBean;
import com.aiju.zyb.bean.OccupationPicBean;
import com.aiju.zyb.bean.OccupationTypeBean;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.model.base.IBaseConfig;
import com.aiju.zyb.presenter.OccupationPresenter;
import com.aiju.zyb.view.photo.PhotoDetailActivity;
import com.aiju.zyb.view.ui.IOccupationView;
import com.aiju.zyb.view.ui.OccupationDetailActivity;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.commonutils.CommonUtil;
import com.jaydenxiao.common.commonwidget.Image;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.jaydenxiao.common.commonwidget.NineGridlayout;
import com.jaydenxiao.common.commonwidget.RecycleViewDivider;
import com.jaydenxiao.common.commonwidget.SpaceItemDecoration;
import com.my.baselibrary.utils.LoadingDialogTools;
import com.my.baselibrary.utils.SettingUtil;

import java.util.ArrayList;
import java.util.List;


public class JobIndexChildFragment extends Fragment implements IOccupationView,IBaseConfig,OnRefreshListener,OnLoadMoreListener {
    private View view;
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView  list_recyclerview;
    private OccupationPresenter occupationPresenter;
    private int cur_page=1;
    private String keyword="";
    private IRecyclerView irc;
    private LoadingTip loadedTip;
    private CommonRecycleViewAdapter<OccupationBean> adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;


    public JobIndexChildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobIndexChildFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobIndexChildFragment newInstance(String param1, String param2) {
        JobIndexChildFragment fragment = new JobIndexChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_job_index_child, container, false);
        initView();
        initData();
        return view;
    }

    /**
     *
     *
     */
    private void initView()
    {
        /*
        swiperefreshlayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefreshlayout);
        // 刷新时，指示器旋转后变化的颜色
        swiperefreshlayout.setColorSchemeResources(R.color.main_blue_dark_resh, R.color.main_blue_dark_resh);
        // mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {swiperefreshlayout.setRefreshing(false);
            }
        });
        list_recyclerview=(RecyclerView)view.findViewById(R.id.list_recyclerview);
        */
        cur_page=1;
        occupationPresenter=new OccupationPresenter(this);
        adapter=new CommonRecycleViewAdapter<OccupationBean>(getContext(),R.layout.occupationitem) {
            @Override
            public void convert(ViewHolderHelper helper, final OccupationBean occupationBean) {
                TextView is_top=helper.getView(R.id.is_top);
                is_top.setVisibility(View.VISIBLE);
                TextView title=helper.getView(R.id.title);
                TextView oc_info=helper.getView(R.id.oc_info);
                ImageView oc_img=helper.getView(R.id.oc_img);
                NineGridlayout  ninegrid=helper.getView(R.id.iv_ngrid_layout);
                RelativeLayout item_re=helper.getView(R.id.item_re);
               // oc_img.setVisibility(View.GONE);
                title.setText(occupationBean.getTitle());
              //  oc_info.setText(occupationBean.getContent());
                String textStr = "<font color=\"#999999\">" + occupationBean.getAuthor() + "    6评论    2小时前</font>";
                oc_info.setText(Html.fromHtml(textStr));
                if(occupationBean.getOccupationPicInfos()!=null && occupationBean.getOccupationPicInfos().size()>0) {
                    List<OccupationPicBean> listPic=occupationBean.getOccupationPicInfos();
                    if(listPic.size()>1)
                    {
                        oc_img.setVisibility(View.GONE);
                        ninegrid.setVisibility(View.VISIBLE);
                        ninegrid.setTotalWidth(SettingUtil.getDisplaywidthPixels()-SettingUtil.dp2px(30));
                        ninegrid.setiPhotoCallBack(new NineGridlayout.IPhotoCallBack() {
                            @Override
                            public void photoCallBack(int pos, String url) {
                                PhotoDetailActivity.launchActivity(getActivity(),pos,url);
                            }
                        });
                        ninegrid.setImagesData(getImage(listPic,0),getImage(listPic,1));
                    }else{
                        oc_img.setVisibility(View.VISIBLE);
                        ninegrid.setVisibility(View.GONE);
                        Glide.with(mContext).load(occupationBean.getOccupationPicInfos().get(0).getThumbnailUrl())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                                .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                                .crossFade().into(oc_img);
                      //  oc_img.setTag(occupationBean.getOccupationPicInfos().get(0).getPicUrl());
                        oc_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                   // String url=v.getTag().toString();
                                    PhotoDetailActivity.launchActivity(getActivity(), 0, occupationBean.getOccupationPicInfos().get(0).getPicUrl());
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }else{
                    oc_img.setVisibility(View.GONE);
                    ninegrid.setVisibility(View.GONE);
                }
                item_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OccupationDetailActivity.launchActivity(getActivity(),occupationBean.getId());
                    }
                });


                /*
                ImageView imageView=helper.getView(R.id.iv_photo);
                Glide.with(mContext).load(photoGirl.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .centerCrop().override(1090, 1090*3/4)
                        .crossFade().into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotosDetailActivity.startAction(mContext,photoGirl.getUrl());
                    }
                });
                */
            }
        };


        irc=(IRecyclerView)view.findViewById(R.id.irc);
        irc.setAdapter(adapter);
       // irc.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
       // irc.addItemDecoration(new SpaceItemDecoration(SettingUtil.dp2px(2)));
      //  irc.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //irc.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        //irc.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.line_bg)));
        irc.setItemAnimator(new DefaultItemAnimator());
        irc.setOnLoadMoreListener(this);
        irc.setOnRefreshListener(this);
        loadedTip=(LoadingTip)view.findViewById(R.id.loadedTip);
    }


    /**
     *
     *
     */
    private  void  initData()
    {
       // LoadingDialogTools.showWaittingDialog(getActivity());
        occupationPresenter.getOccupationList(getActivity(),keyword,Integer.valueOf(mParam1),cur_page,pageSize);
    }


    @Override
    public void  getOccupationType(ResultPageList<OccupationTypeBean> o)
    {

    }


    @Override
    public   void  getOccupationList(ResultPageList<OccupationBean> result)
    {
        if(result.getStatus()==ResultPageList.Http_Success) {
            if(result.getData()!=null && result.getData().size()>0) {
                List<OccupationBean>  list=result.getData();
                if (list != null) {
                    cur_page += 1;
                    if (adapter.getPageBean().isRefresh()) {
                        irc.setRefreshing(false);
                        adapter.replaceAll(list);
                    } else {
                        if (list.size() > 0) {
                            irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                            adapter.addAll(list);
                        } else {
                            irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                        }
                    }
                }
            }else{
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
            }
        }
    }


    @Override
    public void showLoading(String title) {
        if(adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if(adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }


    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        cur_page = 1;
        //发起请求
        irc.setRefreshing(true);
        initData();
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        initData();
    }


    /**
     *
     * 图片转换
     * @param list
     * @return
     */
    private List<Image> getImage(List<OccupationPicBean> list,int type)
    {
        List<Image> iList=new ArrayList<>();
        for (OccupationPicBean o:list)
        {
            Image image=new Image(o.getPicUrl(),200,200);
            iList.add(image);
        }
        if(type==0) {
            if (iList.size() > 3) {
                return iList.subList(0, 3);
            }
        }
        return iList;
    }


    /**
     *
     *
     * @param list
     * @return
     */
    private String  getPic(List<Image> list)
    {
        List<String> pic=new ArrayList<>();
        for (Image m:list)
        {
            pic.add(m.getUrl());
        }

       return   CommonUtil.join(",", pic.toArray(new String[0]));
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
