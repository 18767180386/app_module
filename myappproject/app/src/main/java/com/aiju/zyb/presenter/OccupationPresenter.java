package com.aiju.zyb.presenter;

import android.content.Context;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.OccupationBean;
import com.aiju.zyb.bean.OccupationCommentInfo;
import com.aiju.zyb.bean.OccupationTypeBean;
import com.aiju.zyb.bean.TaokeIndexAdBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.model.IOccupationModel;
import com.aiju.zyb.model.OccupationModel;
import com.aiju.zyb.view.ui.IBaseOccupationView;
import com.aiju.zyb.view.ui.IConmenView;
import com.aiju.zyb.view.ui.IOccupationView;
import com.aiju.zyb.view.ui.uiview.IOuccpationDetailView;
import com.google.gson.Gson;
import com.my.baselibrary.net.HttpException;
import com.my.baselibrary.net.HttpRequestCallback;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.LoadingDialogTools;

import okhttp3.Call;

/**
 * Created by john on 2018/2/5.
 */

public class OccupationPresenter {
    private IBaseOccupationView iBaseOccupationView;
    private IOccupationModel iOccupationModel;

    public OccupationPresenter(IBaseOccupationView taokeView)
    {
        this.iBaseOccupationView=taokeView;
        this.iOccupationModel=new OccupationModel();
    }

    /**
     *
     *
     * @param context
     */
    public void getOccupationTypeList(Context context)
    {
        this.iOccupationModel.getOccupationTypeList(context, new HttpRequestCallback<ResultPageList<OccupationTypeBean>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(ResultPageList<OccupationTypeBean> o) {
                LoadingDialogTools.closeWaittingDialog();
               if(iBaseOccupationView instanceof IOccupationView)
               {
                   ((IOccupationView) iBaseOccupationView).getOccupationType(o);
               }
            }

            @Override
            public void onFailure(Call call, HttpException e) {
                LoadingDialogTools.closeWaittingDialog();
            }
        });
    }


    /**
     *
     * 获取职业信息
     * @param context
     * @param keyword
     * @param type
     * @param current_page
     * @param page_size
     */
    public  void getOccupationList(final Context context, String keyword, int type, int current_page, int page_size)
    {
        iOccupationModel.getOccupationList(context, keyword, type, current_page, page_size, new HttpRequestCallback<ResultPageList<OccupationBean>>() {
            @Override
            public void onStart() {
                iBaseOccupationView.showLoading(context.getString(R.string.loading));
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(ResultPageList<OccupationBean> o) {
              //  LoadingDialogTools.closeWaittingDialog();
                if(iBaseOccupationView instanceof IOccupationView)
                {
                    ((IOccupationView) iBaseOccupationView).getOccupationList(o);
                }
               iBaseOccupationView.stopLoading();
            }

            @Override
            public void onFailure(Call call, HttpException e) {
               // LoadingDialogTools.closeWaittingDialog();
                iBaseOccupationView.showErrorTip(e.getMessage().toString());
            }
        });
    }


    /**
     *
     *
     * @param context
     * @param occ_id
     */
    public   void getOccupationById(final Context context, int occ_id)
    {
        iOccupationModel.getOccupationById(context, occ_id, new HttpRequestCallback<Result<OccupationBean>>() {
            @Override
            public void onStart() {
                iBaseOccupationView.showLoading(context.getString(R.string.loading));
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(Result<OccupationBean> o) {
                HLog.w("net_ret",new Gson().toJson(o));

                if(iBaseOccupationView instanceof IOuccpationDetailView)
                {
                    ((IOuccpationDetailView) iBaseOccupationView).getOccupationView(o);
                }
                iBaseOccupationView.stopLoading();

            }

            @Override
            public void onFailure(Call call, HttpException e) {
                iBaseOccupationView.showErrorTip(e.getMessage().toString());
            }
        });
    }


    /**
     *
     *
     * @param context
     * @param occ_id
     * @param type
     * @param current_page
     * @param page_size
     */
    public   void getCommentList(final Context context, int occ_id, int type, int current_page, int page_size)
    {
        iOccupationModel.getCommentList(context, occ_id, type, current_page, page_size, new HttpRequestCallback<ResultPageList<OccupationCommentInfo>>() {
            @Override
            public void onStart() {
                iBaseOccupationView.showLoading(context.getString(R.string.loading));
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(ResultPageList<OccupationCommentInfo> o) {
                if(iBaseOccupationView instanceof IOuccpationDetailView)
                {
                    ((IOuccpationDetailView) iBaseOccupationView).getCommentView(o);
                }
                iBaseOccupationView.stopLoading();
            }

            @Override
            public void onFailure(Call call, HttpException e) {
                iBaseOccupationView.showErrorTip(e.getMessage().toString());
            }
        });
    }


}
