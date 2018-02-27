package com.aiju.zyb.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.TaokeApplication;
import com.aiju.zyb.bean.HistoryRerordBean;
import com.aiju.zyb.data.dbhelp.DBManager;
import com.aiju.zyb.manage.ThreadManage;
import com.aiju.zyb.view.BaseActivity;
import com.aiju.zyb.view.widget.WarpLinearLayout;
import com.my.baselibrary.utils.HLog;

import java.util.ArrayList;
import java.util.List;


/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private TextView del_all;
    private WarpLinearLayout warpLinearLayout;

    @Override
    protected void initView() {
        showHead(1);
        del_all = (TextView) findViewById(R.id.del_all);
        del_all.setOnClickListener(this);
        warpLinearLayout = (WarpLinearLayout) findViewById(R.id.warpLinearLayout);
        getHisory();
    }


    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.del_all:
                delRecord();
                getHisory();
                break;
        }
    }


    @Override
    public  void doSearch(String key) {
        HLog.w("ret",key);
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("keyword",key);
        intent.putExtras(bundle);
        intent.setClass(this,ProductListActivity.class);
        startActivity(intent);
        if(!TextUtils.isEmpty(key)) {
            saveHistory(key);
        }
    }


    /**
     * 保存搜索记录
     *
     * @param key
     */
    private void saveHistory(String key) {
        try {
            final List<HistoryRerordBean> list = new ArrayList<>();
            HistoryRerordBean bean = new HistoryRerordBean();
            bean.setKeyword(key);
            list.add(bean);
            ThreadManage.getIns().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    DBManager db = new DBManager(TaokeApplication.getContext());
                    try {
                        db.save(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.closeDB();
                    }
                    //  db.closeDB();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    /**
     * 获取记录
     */
    private void getHisory() {
        ThreadManage.getIns().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                DBManager data = new DBManager(TaokeApplication.getContext());
                try {
                    List<HistoryRerordBean> list = data.getScrollData("");

                    Message msg = new Message();
                    msg.what = RETOK;
                    msg.obj = list;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    data.closeDB();
                }
            }
        });
    }

    private static final int ERROR = -1;
    private static final int RETOK = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    break;
                case RETOK: {
                    if (msg != null) {
                        if (msg.obj != null) {
                            List<HistoryRerordBean> list = (ArrayList<HistoryRerordBean>) msg.obj;
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    TextView tv = new TextView(SearchActivity.this);
                                    if(!TextUtils.isEmpty(list.get(i).getKeyword())) {
                                        tv.setText(list.get(i).getKeyword());
                                        tv.setBackgroundResource(R.drawable.textviewshape);
                                        tv.setTextColor(getResources().getColor(R.color.color_959595));
                                        tv.setTextSize(12);
                                        tv.setPadding(15, 10, 15, 10);
                                        tv.setTag(list.get(i).getKeyword());
                                        tv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String obj = v.getTag().toString();
                                                txt_search.setText(obj);
                                                doSearch(obj);
                                            }
                                        });
                                        warpLinearLayout.addView(tv);
                                    }
                                }
                            } else {
                                warpLinearLayout.removeAllViews();
                            }
                        } else {
                            warpLinearLayout.removeAllViews();
                        }
                    }
                }
                break;


            }
        }
    };



    /**
     * 清空记录
     */
    private void delRecord() {
        DBManager db = new DBManager(TaokeApplication.getContext());
        try {
            db.delAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
    }

    @Override
    protected String getTextTitle() {
        return "商品搜索";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initListener() {

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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }


}
