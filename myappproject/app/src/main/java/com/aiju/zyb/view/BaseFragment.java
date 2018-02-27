package com.aiju.zyb.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.view.listener.OnRetryListener;
import com.aiju.zyb.view.listener.OnShowHideViewListener;
import com.aiju.zyb.view.manage.StatusLayoutManager;
import com.aiju.zyb.view.ui.SearchActivity;

import java.util.Timer;
import java.util.TimerTask;

public abstract  class BaseFragment extends Fragment implements IBaseView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected Context mContext;
    protected ProgressDialog progressDialog;
    protected View view;
    protected StatusLayoutManager statusLayoutManager;
    private TextView txt_title;
    protected EditText txt_search;
    private RelativeLayout header_one;
    private RelativeLayout header_two;
    private RelativeLayout header_three;
    private TextView  search_id;
    private ImageView img_back_three;
    private EditText  txt_search_three;
    private TextView  search_id_s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_base, container, false);
        txt_title=(TextView)view.findViewById(R.id.txt_title);
        txt_title.setText(getTextTitle());
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.liMain);

        search_id=(TextView)view.findViewById(R.id.search_id);
        search_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doSearch(txt_search.getText().toString());
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        txt_search=(EditText)view.findViewById(R.id.txt_search);
        txt_search.setFocusable(false);
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        /*
        txt_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        txt_search.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        txt_search.addTextChangedListener(new EditChangedListener());

        txt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ((InputMethodManager) txt_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    doSearch(txt_search.getText().toString());
                    return true;
                }
                return false;
            }
        });
        */
        header_one=(RelativeLayout)view.findViewById(R.id.header_one);
        header_two=(RelativeLayout)view.findViewById(R.id.header_two);
        header_three=(RelativeLayout)view.findViewById(R.id.header_three);
        txt_search_three=(EditText)view.findViewById(R.id.txt_search_three);
        txt_search_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();
            }
        });

        view.findViewById(R.id.img_back_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();
            }
        });




        statusLayoutManager = StatusLayoutManager.newBuilder(mContext)
                .contentView(getLayoutId())
                .netWorkErrorView(getNetWorkErrorView())
                .loadingView(getLoadIngView())
                .netWorkErrorRetryViewId(getTryBtnId())
                .emptyDataRetryViewId(getTryBtnId())
                .emptyDataView(getEmptyData())
                .errorView(getErrorView())
                .onShowHideViewListener(new OnShowHideViewListener() {
                    @Override
                    public void onShowView(View view, int id) {

                    }

                    @Override
                    public void onHideView(View view, int id) {

                    }
                }).onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //  statusLayoutManager.showContent();
                        // tryData();
                    }
                }).build();
        linearLayout.addView(statusLayoutManager.getRootLayout(), 3);
        initView(view);
        initListener();
        initData();
        return view;
    }

    /**
     *
     *展示那个头部
     * @param type
     */
    protected  void showHead(int type)
    {
        if(type==0)
        {
            header_one.setVisibility(View.VISIBLE);
            header_two.setVisibility(View.GONE);
            header_three.setVisibility(View.GONE);
        }else if (type==1){
            header_one.setVisibility(View.GONE);
            header_two.setVisibility(View.VISIBLE);
            header_three.setVisibility(View.GONE);
            view.findViewById(R.id.img_back_new).setVisibility(View.GONE);
            /*
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(txt_search, 0);
                }
            }, 500);
            */
        }else if(type==2)
        {
            header_one.setVisibility(View.GONE);
            header_two.setVisibility(View.GONE);
            header_three.setVisibility(View.VISIBLE);
            /*
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(txt_search_three, 0);
                }
            }, 500);
            */
        }
    }

    protected  void hideBack(int type)
    {
        if(type==0){
            view.findViewById(R.id.img_back).setVisibility(View.GONE);
        }else if(type==1){
            view.findViewById(R.id.img_back_new).setVisibility(View.GONE);
        }
    }


    /**
     * 搜索
     *
     * @param key
     */
    public void doSearch(String key) {

    }

    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // search_edit.setText("还能输入" + (charMaxNum - s.length()) + "字符");

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {

                // doSearch(search_edit.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
            editStart = search_edit.getSelectionStart();
            editEnd = search_edit.getSelectionEnd();
            if (temp.length() > charMaxNum) {
                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                search_edit.setText(s);
                search_edit.setSelection(tempSelection);
            }
            */

        }
    };

    protected  void isShowTitleBar(boolean flag)
    {
        view.findViewById(R.id.header_one).setVisibility(flag?View.VISIBLE:View.GONE);
    }

    protected abstract void initView(View view);
    protected abstract void  initData();
    protected abstract void initListener();
    protected abstract String getTextTitle();

    /**
     * 获取主内容
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 获取无数据内容
     *
     * @return
     */
    protected int getEmptyData() {
        return 0;
        // return R.layout.barnd_emptydata;
    }

    /**
     * 获取重试id
     *
     * @return
     */
    protected int getTryBtnId() {
        return 0;
        //  return R.id.button_try;
    }


    /**
     * 获取错误页
     *
     * @return
     */
    protected int getErrorView() {
        return 0;
    }


    /**
     * 获取加载页
     *
     * @return
     */
    protected int getLoadIngView() {
        return 0;
    }


    /**
     * 获取无网络页
     *
     * @return
     */
    protected int getNetWorkErrorView() {
        return 0;
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSuccess(Result result) {
        Log.w("result", result.getCode() + "sssd");
        if (result.getData() != null) {
            Log.w("result", result.getCode() + "fff");
            statusLayoutManager.showContent();
        } else {
            statusLayoutManager.showEmptyData();
        }
    }

    @Override
    public void loadSuccess(ResultPageList resultPageList) {
        Log.w("result", resultPageList.getCode() + "sssd");
        if (resultPageList.getData() != null) {
            Log.w("result", resultPageList.getCode() + "sssdff");
            statusLayoutManager.showContent();
        } else {
            statusLayoutManager.showEmptyData();
        }
    }

    @Override
    public void loadFailure(String errorMsg) {
        Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        if (!getActivity().isFinishing() && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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


