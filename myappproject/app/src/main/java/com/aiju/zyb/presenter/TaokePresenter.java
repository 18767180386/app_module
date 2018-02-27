package com.aiju.zyb.presenter;

import android.content.Context;
import android.util.Log;

import com.aiju.zyb.bean.TaokeIndexAdBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.model.ITaokeModel;
import com.aiju.zyb.model.TaokeModel;
import com.aiju.zyb.view.ui.IConmenView;
import com.aiju.zyb.view.ui.IProListView;
import com.aiju.zyb.view.ui.ISortView;
import com.aiju.zyb.view.ui.ITaokeView;
import com.my.baselibrary.net.HttpException;
import com.my.baselibrary.net.HttpRequestCallback;

import okhttp3.Call;

/**
 * Created by AIJU on 2017-04-28.
 */

public class TaokePresenter {
    private IConmenView taokeView;
    private ITaokeModel iTaokeModel;

    public TaokePresenter(IConmenView taokeView)
    {
        this.taokeView=taokeView;
        this.iTaokeModel=new TaokeModel();
    }


    /**
     * 获取首页数据
     * @param context
     * @param type
     * @param curPage
     * @param pageSize
     */
    public  void  getIndexList(Context context,int type,int curPage,int pageSize)
    {
        this.iTaokeModel.getIndexList(context,type,curPage,pageSize,new HttpRequestCallback<ResultPageList<TaokeItemBean>>() {
            @Override
            public void onStart() {
                //开始加载
               // taokeView.showLoadingDialog();
            }

            @Override
            public void onFinish() {
                //加载完毕
              //  taokeView.closeLoadingDialog();
            }

            @Override
            public void onResponse(ResultPageList<TaokeItemBean> result) {
                ((ITaokeView)taokeView).getIndextList(result);
              //  HLog.w("ret",result);
             //   marketView.loadSuccess(result);
               // ((IMarketInfoView) marketView).getApplyInfo(result);

            }


            @Override
            public void onFailure(Call call, HttpException e) {
                //加载失败
                Log.w("result", e.getMessage().toString());
               // taokeView.loadFailure(e.getMessage());
            }
        });
    }


    /**
     *广告数据
     * @param context
     * @param type
     */
    public  void  getIndexAdList(Context context,int type)
    {
        this.iTaokeModel.getIndexAdList(context,type,new HttpRequestCallback<Result<TaokeIndexAdBean>>() {
            @Override
            public void onStart() {
                //开始加载
               // taokeView.showLoadingDialog();
            }

            @Override
            public void onFinish() {
                //加载完毕
               // taokeView.closeLoadingDialog();
            }

            @Override
            public void onResponse(Result<TaokeIndexAdBean> result) {
                ((ITaokeView)taokeView).getIndexAdList(result);
                //   marketView.loadSuccess(result);
                // ((IMarketInfoView) marketView).getApplyInfo(result);

            }


            @Override
            public void onFailure(Call call, HttpException e) {
                //加载失败
                Log.w("result", e.getMessage().toString());
               // taokeView.loadFailure(e.getMessage());
            }
        });
    }


    /**
     *
     * 获取商品分类
     * @param context
     */
    public  void  getProSortList(Context context)
    {
        this.iTaokeModel.getProSortList(context,new HttpRequestCallback<ResultPageList<TaokeProSort>>() {
            @Override
            public void onStart() {
                //开始加载
                // taokeView.showLoadingDialog();
            }

            @Override
            public void onFinish() {
                //加载完毕
                // taokeView.closeLoadingDialog();
            }

            @Override
            public void onResponse(ResultPageList<TaokeProSort> result) {
                if(taokeView instanceof  ISortView) {
                    ((ISortView) taokeView).getProSortList(result);
                }else {
                    ((ITaokeView) taokeView).getProSortList(result);
                }
                //   marketView.loadSuccess(result);
                // ((IMarketInfoView) marketView).getApplyInfo(result);

            }


            @Override
            public void onFailure(Call call, HttpException e) {
                //加载失败
                Log.w("result", e.getMessage().toString());
                // taokeView.loadFailure(e.getMessage());
            }
        });
    }


    /**
     * 获取指定分类商品
     * @param context
     * @param type
     * @param limit
     */
    public  void getProListByType(Context context,int type,int limit)
    {
        this.iTaokeModel.getProListByType(context,type,limit,new HttpRequestCallback<ResultPageList<TaokeItemBean>>() {
            @Override
            public void onStart() {
                //开始加载
                // taokeView.showLoadingDialog();
            }

            @Override
            public void onFinish() {
                //加载完毕
                //  taokeView.closeLoadingDialog();
            }

            @Override
            public void onResponse(ResultPageList<TaokeItemBean> result) {
                ((ISortView)taokeView).getProListByType(result);
                //  HLog.w("ret",result);
                //   marketView.loadSuccess(result);
                // ((IMarketInfoView) marketView).getApplyInfo(result);

            }


            @Override
            public void onFailure(Call call, HttpException e) {
                //加载失败
                Log.w("result", e.getMessage().toString());
                // taokeView.loadFailure(e.getMessage());
            }
        });
    }


    /**
     *
     * 商品搜索
     * @param context
     * @param keyword
     * @param type
     * @param offset
     * @param limit
     */
    public   void  proSearchList(Context context,String keyword,int type,int offset,int limit)
    {
        this.iTaokeModel.proSearchList(context,keyword,type,offset,limit,new HttpRequestCallback<ResultPageList<TaokeItemBean>>() {
            @Override
            public void onStart() {
                //开始加载
                // taokeView.showLoadingDialog();
            }

            @Override
            public void onFinish() {
                //加载完毕
                //  taokeView.closeLoadingDialog();
            }

            @Override
            public void onResponse(ResultPageList<TaokeItemBean> result) {
                 ((IProListView)taokeView).getProList(result);
            }


            @Override
            public void onFailure(Call call, HttpException e) {
                //加载失败
                Log.w("result", e.getMessage().toString());
                // taokeView.loadFailure(e.getMessage());
            }
        });
    }

}
