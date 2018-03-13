package com.aiju.zyb.view.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.google.gson.Gson;
import com.jaydenxiao.common.commonutils.CommonUtil;
import com.jaydenxiao.common.commonutils.DateTool;
import com.jaydenxiao.common.commonutils.GlideUtils;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonwidget.Image;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.jaydenxiao.common.commonwidget.XListView;
import com.my.baselibrary.base.BaseApplication;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.ToastUtil;

import org.json.JSONObject;

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
    private RelativeLayout comment_re;
    private TextView unread_msg;
    private ImageView zan_img;
    private ImageView express_share;
    private Dialog mEvaluteDialog;
    private OccupationDetailActivity instance=null;
    private EditText comment_edit_tip;
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
        instance=this;
        Bundle bundle=getIntent().getExtras();
        occ_id=bundle.getInt("oid",0);
        occupationPresenter=new OccupationPresenter(this);
        layoutInflator = LayoutInflater.from(this);
        occupation_list=(XListView)findViewById(R.id.occupation_list);
        loadedTip=(LoadingTip)findViewById(R.id.loadedTip);
        comment_edit_tip=(EditText)findViewById(R.id.comment_edit_tip);
        comment_edit_tip.setCursorVisible(false);
        comment_edit_tip.setFocusable(false);
        comment_edit_tip.setFocusableInTouchMode(false);
        comment_edit_tip.setOnClickListener(this);
        comment_re=(RelativeLayout)findViewById(R.id.comment_re);
        unread_msg=(TextView)findViewById(R.id.unread_msg);
        zan_img=(ImageView)findViewById(R.id.zan_img);
        express_share=(ImageView)findViewById(R.id.express_share);
        createEvaluteDialog();
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
                HLog.w("resh_1","1");
            }

            @Override
            public void onLoadMore() {
                //loadMoreData();
                HLog.w("resh_1","2");
                if (isNoMoreData) {
                    HLog.w("resh_1","3");
                   // UtilToast.show(self, "没有更多评论");
                    onLoad();
                    return;
                }
                HLog.w("resh_1","4");
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
        occupationPresenter.getCommentList(this,occ_id,sortType,pageIndex,pageSize);
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
             case R.id.comment_edit_tip:
                 showEvaluateDialog();
                 break;
             case R.id.cancle_text:
                 dismissEvaluateDialog();
                 break;
             case R.id.send:
                 sendComment();
                 break;

         }
    }

    private EditText comment_edit;
    private TextView cancle_text;
    private TextView send;
    @SuppressLint("InlinedApi")
    private void createEvaluteDialog() {
        final View editView = LayoutInflater.from(instance).inflate(R.layout.comment_list_footer_view, null);
        comment_edit = (EditText) editView.findViewById(R.id.comment_edit);
        send = (TextView) editView.findViewById(R.id.send);
        send.setEnabled(false);
        send.setOnClickListener(this);
        comment_edit.addTextChangedListener(new TextWatcher() {
            private int oldSelStart;
            private int textChangeCount;
            private int textCount;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                 // keyword.setText("");
                //   managerSendButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                textCount = s.toString().length();
                textChangeCount = after - start;

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() > 0) {
                    send.setEnabled(true);
                    send.setTextColor(instance.getResources().getColor(R.color.theme_color));
                } else {
                    send.setEnabled(false);
                    send.setTextColor(instance.getResources().getColor(R.color.color_99));
                }

            }

        });
        cancle_text=(TextView)editView.findViewById(R.id.cancle_text);
        cancle_text.setOnClickListener(this);


        mEvaluteDialog = new Dialog(instance, R.style.DialogFullscreen);
        mEvaluteDialog.setContentView(editView);
        mEvaluteDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mEvaluteDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        Window w = mEvaluteDialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;//显示dialog的时候,就显示软键盘
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;//就是这个属性导致不能获取
        lp.x = 0;
        lp.width = RadioGroup.LayoutParams.MATCH_PARENT;//SettingUtil.getDisplaywidthPixels()
        lp.gravity = Gravity.BOTTOM;
        mEvaluteDialog.onWindowAttributesChanged(lp);
        mEvaluteDialog.setCanceledOnTouchOutside(true);
        //mEvaluteDialog.setOnCancelListener(listener)
        mEvaluteDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
            //    if (comment != null) {
                    //comment = null;
             //   }
                clearText();
                if (comment_edit != null) {
                    comment_edit.setHint("说点什么吧 ");
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //  smilyDeal();
                    }
                }, 50);

            }

        });
    }

    /**
     *
     * 清除
     */
    public void clearText() {
        if (comment_edit != null) {
            comment_edit.getText().clear();
        }
    }

    private void showEvaluateDialog() {
        if (null != mEvaluteDialog && !mEvaluteDialog.isShowing()) {
            mEvaluteDialog.show();
            // mEvaluteDialog.setf

            //  mEvaluteDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            // comment_edit.requestFocus();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    comment_edit.requestFocus();
                    ((InputMethodManager)instance.getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(comment_edit, 0);
                }
            },100);

        }
    }

    private void dismissEvaluateDialog() {
        if (null != mEvaluteDialog && mEvaluteDialog.isShowing()) {
            mEvaluteDialog.dismiss();
        }
    }

    /*
       * 发表评论
       */
    private long lastClickTime = 0;
    private void sendComment() {

        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 3000) {
            ToastUtil.setToast("请于三秒后再点击");
            return;
        }
        lastClickTime = time;
        String content = comment_edit.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.setToast("评论内容不能为空");
            return;
        }
        try {
            keybord(true, 1);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    dismissEvaluateDialog();
                }
            }, 50);

        } catch (Exception e) {
            e.printStackTrace();
        }
        occupationPresenter.addComment(this,content,"2",occ_id+"");
        /*
        DialogTools.showWaittingDialog(instance);
        DailyApi.getIns().addComment(DataManager.getInstance(BaseApplication.getContext()).getUser().getVisit_id(), 1, DataManager.getInstance(BaseApplication.getContext()).getUserID() + "", vo.getId(), vo.getUser_id(), (comment != null ? comment.getUser_id() : ""), content, (comment != null ? comment.getId() : ""), new IRsCallBack<String>() {
            @Override
            public void successful(String data, String json) {
                DialogTools.closeWaittingDialog();
                HLog.w("reply_post", json);
                try {
                    JSONObject retjson = new JSONObject(json);
                    if (retjson.getString("code").equals("0")) {
                        UtilToast.show("提交成功");
                        clearText();
                        if (comment_edit != null) {
                            comment_edit.setHint("写评论");
                        }
                        isNotice=true;
                        initData(true,1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    UtilToast.show("网络异常");
                }
            }

            @Override
            public boolean fail(String data, String json) {
                DialogTools.closeWaittingDialog();
                return false;
            }
        }, String.class);
         */
    }
    private void keybord(boolean flag, int type) {
        if (type == 1) {
            InputMethodManager imm = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (flag) {
                imm.hideSoftInputFromWindow(comment_edit.getWindowToken(), 0);
            } else {
                imm.showSoftInput(comment_edit, 0);
            }

        } else {
            InputMethodManager imm = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(comment_edit,
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);

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
                    onLoad();
                    occupation_list.setmTotalItemCount();
                 }else{
                    occupationAdapter.addDatas(cList);
                    onLoad();
                  }
            }else{
                isNoMoreData=(pageIndex==1?false:true);
                onLoad();
            }
        }
    }

    @Override
    public   void  addCommentView(Result<String> o)
    {
        HLog.w("net_p",new Gson().toJson(o));
        if(o.getStatus()==Result.Http_Success)
        {
            ToastUtil.setToast(o.getMsg());
            isNoMoreData = false;
            pageIndex = 1;
            loadData();
        }else{
            ToastUtil.setToast(o.getMsg());
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
