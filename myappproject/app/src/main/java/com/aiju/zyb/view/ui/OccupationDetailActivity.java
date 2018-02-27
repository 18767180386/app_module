package com.aiju.zyb.view.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.OccupationBean;
import com.aiju.zyb.bean.OccupationCommentInfo;
import com.aiju.zyb.bean.OccupationPicBean;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.model.base.IBaseConfig;
import com.aiju.zyb.presenter.OccupationPresenter;
import com.aiju.zyb.view.BaseActivity;
import com.aiju.zyb.view.adapter.OccupationAdapter;
import com.aiju.zyb.view.photo.PhotoDetailActivity;
import com.aiju.zyb.view.ui.uiview.IOuccpationDetailView;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jaydenxiao.common.commonutils.CommonUtil;
import com.jaydenxiao.common.commonutils.DateTool;
import com.jaydenxiao.common.commonutils.GlideUtils;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonwidget.Image;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.jaydenxiao.common.commonwidget.XListView;

import java.util.ArrayList;
import java.util.List;

public class OccupationDetailActivity extends BaseActivity implements IOuccpationDetailView,IBaseConfig,View.OnClickListener {
    private XListView  occupation_list;
    private LoadingTip loadedTip;
    private LayoutInflater layoutInflator;
    private int pageIndex = 0;
    private boolean isNoMoreData = false;
    private OccupationPresenter occupationPresenter;
    private int occ_id=0;
    private ImageView user_img;
    private TextView user_name;
    private TextView article_type;
    private TextView occupation_title;
    private TextView occu_info;
    private TextView add_friend;
    private OccupationAdapter occupationAdapter;
    private TextView content;
    private LinearLayout image_li;
    public static void launchActivity(Context context, int oid) {
        Intent intent = new Intent(context, OccupationDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("oid",oid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected    void initView()
    {
        showHead(0);
        Bundle bundle=getIntent().getExtras();
        occ_id=bundle.getInt("oid",0);
        occupationPresenter=new OccupationPresenter(this);
        layoutInflator = LayoutInflater.from(this);
        occupation_list=(XListView)findViewById(R.id.occupation_list);
        loadedTip=(LoadingTip)findViewById(R.id.loadedTip);
        View view = layoutInflator.inflate(R.layout.occupationdetailhead, null);
        occupation_title=(TextView)view.findViewById(R.id.occupation_title);
        user_img=(ImageView)view.findViewById(R.id.user_img);
        user_name=(TextView)view.findViewById(R.id.user_name);
        article_type=(TextView)view.findViewById(R.id.article_type);
        occu_info=(TextView)view.findViewById(R.id.occu_info);
        add_friend=(TextView)view.findViewById(R.id.add_friend);
        add_friend.setOnClickListener(this);
        content=(TextView)view.findViewById(R.id.content);
        image_li=(LinearLayout)view.findViewById(R.id.image_li);
        try {
            this.occupation_list.addHeaderView(view);
        } catch (Exception e) {

        }
        occupation_list.setPullLoadEnable(true);
        occupation_list.setPullRefreshEnable(true);
        occupation_list.setVerticalScrollBarEnabled(false);
        //comlist.setIsResh(true);
        occupation_list.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                //loadData(false);
                isNoMoreData = false;
               // isNotice=false;
                pageIndex = 1;
                loadData();
               // initData(true,0);
            }

            @Override
            public void onLoadMore() {
                //loadMoreData();
                if (isNoMoreData) {
                   // UtilToast.show(self, "没有更多评论");
                    onLoad();
                    return;
                }
                loadData();
                //isNotice=false;
              //  initData(false,0);
            }

            @Override
            public void onShowTopView(int firstVisibleItem) {

            }
        });
        occupationAdapter=new OccupationAdapter(this);
        occupation_list.setAdapter(occupationAdapter);
    }

    /**
     * 停止listview刷新
     */
    private void onLoad() {
        occupation_list.stopRefresh();
        occupation_list.stopLoadMore();
       // occupation_list.setNoMoreData(isNoMoreData);
        occupation_list.setIshowFoot(isNoMoreData);
    }


    @Override
    protected void  initData()
    {
        occupationPresenter.getOccupationById(this,occ_id);
        pageIndex=1;
        loadData();
    }


    /**
     *
     * 加载评论
     */
    private  void  loadData()
    {
        occupationPresenter.getCommentList(this,occ_id,1,pageIndex,pageSize);
    }



    @Override
    protected   void  initListener()
    {

    }
    @Override
    public   void onClick(View v)
    {
         switch (v.getId())
         {
             case R.id.add_friend:
                 break;
         }
    }


    @Override
    public void showLoading(String title) {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
        loadedTip.setTips(msg);
        /*
        if(adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
        */
    }



    @Override
    public    void  getOccupationView(Result<OccupationBean> o)
    {
         if(o.getStatus()==Result.Http_Success)
         {
             if(o.getData()!=null)
             {
                 GlideUtils.getCicleImg(this,o.getData().getUserInfo().getUserImg(),user_img);
                 user_name.setText(o.getData().getUserInfo().getNickName());
                 occupation_title.setText(o.getData().getTitle());
                 occu_info.setText(TimeUtil.friendly_time(TimeUtil.stampToDate(o.getData().getCreatetime()+""))+"  "+o.getData().getAuthor());
                 content.setText(o.getData().getContent());
                 if(o.getData().getOccupationPicInfos()!=null && o.getData().getOccupationPicInfos().size()>0)
                 {
                     image_li.removeAllViews();
                     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                     List<OccupationPicBean> pList=o.getData().getOccupationPicInfos();
                   //  WindowManager wm = this.getWindowManager();
                     //int width = wm.getDefaultDisplay().getWidth();
                     final List<String> pic=new ArrayList<>();
                     for (OccupationPicBean p:pList)
                     {
                         pic.add(p.getPicUrl());
                     }
                     for (int i=0;i<pList.size();i++)
                     {
                         final int m=i;
                         ImageView imageView=new ImageView(this);
                         imageView.setAdjustViewBounds(true);//设置图片自适应，只是这句话必须结合下面的setMaxWidth和setMaxHeight才能有效果。
                          //下面必须使用LinearLayout，如果使用ViewGroup的LayoutParams，则会报空指针异常。
                       //  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                         imageView.setLayoutParams(layoutParams);
                         //imageView.setMaxWidth(width);
                       //  imageView.setMaxHeight(width * 3);// 这里其实可以根据需求而定，我这里测试为最大宽度的5倍
                         imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                       //  imageView.setImageResource();

                         GlideUtils.glideImageLoad(this,pList.get(i).getThumbnailUrl(),imageView);
                         imageView.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 PhotoDetailActivity.launchActivity(OccupationDetailActivity.this,m, CommonUtil.join(",", pic.toArray(new String[0])));
                             }
                         });
                         image_li.addView(imageView);
                     }
                 }
             }
         }
    }


    @Override
    public   void  getCommentView(ResultPageList<OccupationCommentInfo> o)
    {
        if(o.getStatus()==ResultPageList.Http_Success)
        {
            if(o.getData()!=null && o.getData().size()>0)
            {
                isNoMoreData=false;
                pageIndex++;
                List<OccupationCommentInfo> cList=o.getData();
                if(pageIndex==2) {
                  occupationAdapter.setData(cList);
                 }else{
                  occupationAdapter.addDatas(cList);
                  }
                 onLoad();
            }else{
                isNoMoreData=true;
                onLoad();
            }
        }
    }

    @Override
    protected   String  getTextTitle()
    {
        return  "详情";
    }


    @Override
    protected int getLayoutId()
    {
        return  R.layout.activity_occupation_detail;
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

}
