package com.aiju.zyb.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Timer;
import java.util.TimerTask;

public abstract  class BaseActivity  extends FragmentActivity  implements IBaseView {
    protected ProgressDialog progressDialog;
    protected StatusLayoutManager statusLayoutManager;
    public SystemBarTintManager tintManager = null;
    private TextView title_show;
    protected EditText txt_search;
    private RelativeLayout  header_one;
    private RelativeLayout  header_two;
    private TextView  search_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tintManager = new SystemBarTintManager(this);
        setContentView(R.layout.activity_base);
        LinearLayout liMain=(LinearLayout)findViewById(R.id.activity_base);
        search_id=(TextView)findViewById(R.id.search_id);
        search_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(txt_search.getText().toString());
            }
        });
        txt_search=(EditText)findViewById(R.id.txt_search);
        txt_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        txt_search.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        txt_search.addTextChangedListener(new EditChangedListener());

        txt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ((InputMethodManager) txt_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    doSearch(txt_search.getText().toString());
                    return true;
                }
                return false;
            }
        });
        header_one=(RelativeLayout)findViewById(R.id.header_one);
        header_two=(RelativeLayout)findViewById(R.id.header_two);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        findViewById(R.id.img_back_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        title_show=(TextView)findViewById(R.id.txt_title);
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
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
        liMain.addView(statusLayoutManager.getRootLayout(), 2);

        initView();
        initListener();
        initData();
        title_show.setText(getTextTitle());
    }



    @Override
    public void setContentView(int layoutResID) {
        // android:background="@color/colorAccent"
        setContentView(layoutResID, true, true, R.color.theme_color);
    }

    public void setContentView(int layoutResID, boolean isHade) {
        super.setContentView(layoutResID);
        tintManager.setStatusBarTintEnabled(true);

    }


    public void setContentView(int layoutResID, int statuColor) {
        setContentView(layoutResID, true, true, statuColor);
    }


    public void setContentView(int layoutResID, boolean isInitStatusBar, boolean isFitsSystemWindows, int statuColor) {
        if (isInitStatusBar) {
            super.setContentView(layoutResID);
            setStatusBar(statuColor);
        } else {
            super.setContentView(layoutResID);
            return;
        }
        if (isFitsSystemWindows)
            setRootView(true);
        else
            setRootView(false);
    }

    public void setNoStautsBar() {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(false);
        rootView.setClipToPadding(false);
        tintManager.setStatusBarTintEnabled(false);
        tintManager.setNavigationBarTintEnabled(false);
        rootView.requestLayout();
//            tintManager.setmStatusBarAvailable(false);
        this.getWindow().getDecorView().requestLayout();
    }

    protected void setStatusBar(int statuColor) {
        // enable status bar tint


        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(false);
        tintManager.setTintResource(statuColor);
    }


    public void setRootView(boolean isHade) {
        if (isHade) {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
            rootView.requestLayout();
//            tintManager.setmStatusBarAvailable(true);
            this.getWindow().getDecorView().requestLayout();
        } else {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(false);
            tintManager.setStatusBarTintEnabled(false);
            tintManager.setNavigationBarTintEnabled(false);
            rootView.requestLayout();
//            tintManager.setmStatusBarAvailable(false);
            this.getWindow().getDecorView().requestLayout();
        }
    }



    protected abstract void initListener();

    protected abstract void initView();

    protected abstract void initData();
    protected abstract String getTextTitle();


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
        }else{
            header_one.setVisibility(View.GONE);
            header_two.setVisibility(View.VISIBLE);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(txt_search, 0);
                }

            }, 500);
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


    /**
     *
     * 获取主内容
     * @return
     */
    protected abstract int getLayoutId();


    /**
     *
     * 获取无数据内容
     * @return
     */
    protected int getEmptyData()
    {
        return 0;
       // return R.layout.barnd_emptydata;
    }


    /**
     *
     * 获取重试id
     * @return
     */
    protected  int getTryBtnId()
    {
        return 0;
      //  return R.id.button_try;
    }


    /**
     *
     * 获取错误页
     * @return
     */
    protected  int getErrorView()
    {
        return  0;
    }


    /**
     *
     * 获取加载页
     * @return
     */
    protected  int getLoadIngView()
    {
        return 0;
    }


    /**
     *
     * 获取无网络页
     * @return
     */
    protected  int getNetWorkErrorView()
    {
        return 0;
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!isFinishing() && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static final int REQUEST_CODE = 0;

    private String[] permissions;

    public interface PermissionCallBackListening {
        void permissionGranted();

        void permissionDenied();
    }

    public PermissionCallBackListening listening;

    /**
     *
     * @param title
     * @param content
     * @param action  跳转的权限设置
     */
    public void showNoPermissionTip(String title, String content, final boolean isContinue, String action)
    {

        /*
        final ConfirmDialog tipDialog = new ConfirmDialog(this);
        tipDialog.setListener(new ConfirmDialog.ConfirmDialogListener() {
            @Override
            public void confirmListener() {
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", "com.wadao.wdfx", null);
                    intent.setData(uri);
                    startActivity(intent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }

            @Override
            public void cancelListener() {
                if (!isContinue)
                    finish();
                tipDialog.dismiss();
            }
        });
        tipDialog.show(title, content, "以后再说", "去授权");
        */
    }

    /**
     * 设置有多少权限需要加入提示(大于sdk23可用)
     */
    public void setPermissions(String[] permissions, PermissionCallBackListening listening) {
        this.permissions = permissions;
        this.listening = listening;

        if (!checkPermissions()) {
            requestSinglePermission();
        } else {
            if (listening != null)
                listening.permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (userGrantedAllPermissions(grantResults)) {
                // 授权成功
                if (listening != null)
                    listening.permissionGranted();
            } else {
                // 授权失败
                if (listening != null)
                    listening.permissionDenied();
            }
        }
    }

    /**
     * 判断设置的权限是否已被授权
     */
    public boolean checkPermissions() {
        if (permissions != null) {//在有权限请求的情况下去执行
            for (int i = 0; i < permissions.length; i++) {
                if (checkSelfsPermission(permissions[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否授权
     */
    public boolean checkSelfsPermission(String manifestPermission) {

        return ContextCompat.checkSelfPermission(this, manifestPermission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 对用户进行授权请求
     */
    public void requestSinglePermission() {
        ActivityCompat.requestPermissions(this, permissions,
                REQUEST_CODE);
    }

    /**
     * 判断用户是否全都点击了授权
     */
    public boolean userGrantedAllPermissions(int[] grantResults) {
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            // TODO: 2016/10/14

            if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    protected void JumpToTargetActivity(Activity activity, Class<? extends Activity> cls) {
        JumpToTargetActivity(activity, cls, null, false);
    }

    protected void JumpToTargetActivity(Activity activity, Class<? extends Activity> cls, boolean isClearStak) {
        JumpToTargetActivity(activity, cls, null, isClearStak);
    }

    protected void JumpToTargetActivity(Activity activity, Class<? extends Activity> cls, Bundle bundle, boolean isClearStak) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("id", cls.getCanonicalName());
        if (isClearStak) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    protected void JumpToTargetActivityForResult(Activity activity, Class<? extends Activity> cls, int requestCode) {
        JumpToTargetActivityForResult(activity, cls, null, requestCode);
    }

    protected void JumpToTargetActivityForResult(Activity activity, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        JumpToTargetActivityForResult(activity, cls, bundle, requestCode, false);
    }

    protected void JumpToTargetActivityForResult(Activity activity, Class<? extends Activity> cls, Bundle bundle, int requestCode, boolean isClearStak) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("id", cls.getCanonicalName());
        if (isClearStak) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public final static void show(Activity activity, Class<? extends Activity> cls) {
        show(activity, cls, null);
    }

    public final static void show(Activity activity, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("id", cls.getCanonicalName());
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public final static void show(Context activity, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("id", cls.getCanonicalName());
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public final static void show(Activity activity, Class<? extends Activity> cls, Bundle bundle, boolean flags) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("id", cls.getCanonicalName());

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flags) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }


    public final static void showForResult(Activity activity, Class<? extends Activity> cls, Bundle bundle, int requestCode, boolean flags) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("id", cls.getCanonicalName());
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flags) {
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivityForResult(intent, requestCode);
    }



}
