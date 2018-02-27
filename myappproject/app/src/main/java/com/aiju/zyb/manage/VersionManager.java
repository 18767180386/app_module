package com.aiju.zyb.manage;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.TaokeApplication;
import com.aiju.zyb.bean.TaokeUpdateBean;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.data.DataManager;
import com.aiju.zyb.model.TaokeModel;
import com.aiju.zyb.util.Utils;
import com.aiju.zyb.view.widget.MikyouCommonDialog;
import com.aiju.zyb.view.widget.SystemUpdateDialog;
import com.my.baselibrary.base.BaseApplication;
import com.my.baselibrary.net.HttpException;
import com.my.baselibrary.net.HttpRequestCallback;
import com.my.baselibrary.utils.FileUtil;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.SettingUtil;
import com.my.baselibrary.utils.TimeUtils;
import com.my.baselibrary.utils.ToastUtil;
import com.my.baselibrary.utils.Util;

import java.io.File;

import okhttp3.Call;

/**
 * Created by AIJU on 2017-06-05.
 */

public class VersionManager {
    protected static final int DOWNLOAD_SUCCESS = 1;
    protected static final int DOWNLOAD_ERROR = -1;

    public final static String VERSION_KEY = "yhk_version_key";
    private static final String PREFERENCES_APK_NAME = "update_info";
    private static final String PREFERENCES_PATH = "path";
    private static final String PREFERENCES_VERSION = "version";
    private String  update_tip= Utils.getAppInfo(TaokeApplication.getContext(),0)+"下载更新";
    private static String  app_name="youhuike.apk";

    private IDownStatus mDownLoadStatus;

    private  IRequestDataStatus irequestdatalistenter;

    private Context mContext;

    private int type=0;  //1  启动时调用   2 设置时调用

    private int status=0;  // 1 有安装包   2 无安装包

    private  String version_update="";
    private String  update_info="";
    private String  update_data="";
    private boolean isOnUpdate=false;

    public VersionManager(Context context) {
        this.mContext = context;
    }


    public void initiate(int type,IRequestDataStatus  irequestdatalistenter) {
        this.irequestdatalistenter=irequestdatalistenter;
        updateSystem(irequestdatalistenter);
    }


    /***
     *
     * 设置启动类型
     * @param type
     */
    public  void  setType(int type)
    {
        this.type=type;
    }



    /**
     *请求更新数据
     */
    public  void  updateSystem(final IRequestDataStatus  irequestdatalistenter)
    {
        if(TaokeApplication.getContext()==null)
        {
            HLog.w("base_1","yes");
        }else {
            HLog.w("base_1","no");
        }

        try {
            String packageName = TaokeApplication.getContext().getPackageName();
            String versionCode = Utils.getAppInfo(TaokeApplication.getContext(), 2);
            TaokeModel tao = new TaokeModel();
            tao.versionUpdate(TaokeApplication.getContext(), packageName, versionCode, new HttpRequestCallback<Result<TaokeUpdateBean>>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onResponse(Result<TaokeUpdateBean> result) {
                    //  HLog.w("ret",new Gson().toJson(result));
                    if (result.getCode().equals("000")) {
                        version_update = result.getData().getVersionupdate() + "";
                        update_info = result.getData().getUpdateinfo();
                        update_data = result.getData().getDownloadurl();
                        if (irequestdatalistenter != null) {
                            irequestdatalistenter.requestSuccess(result.getData());
                        } else {
                            if(isShowUpdateTimeEnough()) {
                                showUpdateTip(); //isShowUpdateTimeEnough() &&
                            }
                        }
                    } else {
                        if (irequestdatalistenter != null) {
                            irequestdatalistenter.requestFail();
                        }
                    }
                }


                @Override
                public void onFailure(Call call, HttpException e) {
                    //加载失败
                    Log.w("result", e.getMessage().toString());
                    // taokeView.loadFailure(e.getMessage());

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     * 弹出更新提示框
     */
    public void showUpdateTip() {

        if(!checkIsUpdate())
        {
            return;
        }
        if(TextUtils.isEmpty(version_update) || TextUtils.isEmpty(update_info) || TextUtils.isEmpty(update_data))
        {
            return;
        }

        new MikyouCommonDialog(mContext, R.layout.versionupgrade, "发现新版本", "升级去", "下次再说")
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        isOnUpdate = true;
                        downApk(update_data);
                        dialogInterface.dismiss();
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {
                        //Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        if(version_update.equals("2"))
                        {
                            // activity.finish();
                            // TODO: 2017/1/13 why finishAllActivity

                         //   BaseApplication.getInstance().finishAllActivity();
                            System.exit(0);

                            dialogInterface.dismiss();
                        }else{
                            dialogInterface.dismiss();
                        }
                    }
                }).setDialogViewListener(new MikyouCommonDialog.IMainCallBackView() {
            @Override
            public void callBackDialogView(DialogInterface dialogInterface, View view) {
                versionDeal(view);
            }
        }).setDialogInfo(0, 0, 0,32,TaokeApplication.getContext().getResources().getColor(R.color.ec_tab_press)).showDialog();


    }


    /**
     *
     * 升级结果处理
     * @param view
     */
    private  void  versionDeal(View view)
    {

        LinearLayout version_li=(LinearLayout)view.findViewById(R.id.version_li);
        version_li.removeAllViews();
        String[] tip=update_info.split("\\|");
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, SettingUtil.dp2px(40));
        lp1.leftMargin=SettingUtil.dp2px(15);
        if(tip.length>0)
        {
            for (int i=0;i<tip.length;i++) {
                TextView text = new TextView(mContext);
                text.setLayoutParams(lp1);
                text.setTextSize(SettingUtil.px2sp(mContext,28));
                text.setTextColor(mContext.getResources().getColor(R.color.color_66));
                text.setGravity(Gravity.CENTER_VERTICAL);
                text.setText(tip[i]);
                version_li.addView(text);
            }
        }
    }


    /**
     *
     * 检测是否要更新
     * @return
     */
    public  boolean  checkIsUpdate()
    {
        if(version_update.equals("1") || version_update.equals("2"))
        {
            return true;
        }
        return  false;
    }

    /**
     *
     * 下载方式2
     * @param url
     */
    private void  downApk(String url)
    {
        String filePath= FileUtil.getDownloadUrl();
        if (filePath == null) {
             ToastUtil.setToast("请插入储存卡");
            return;
        }

        if(updateType==0)
        {
            FileUtil.download(url, filePath, handler2);
            showNotifacation();
        }else{
            pd = new SystemUpdateDialog(mContext, R.style.more_dialog,update_tip);
            pd.setCancelable(false);
            pd.show();
            FileUtil.download(url, filePath, handler3);
        }
        Util.putPreferenceBoolean(BaseApplication.getInstance().getContext(), VERSION_KEY,true);

        //  UtilToast.show(Util.getPreferenceBoolean(BaseApplication.getContext(), VersionKeyCache.VERSION_KEY,false)+"22112");


    }


    /**
     * 安装apk
     *
     * @param msg
     */
    private void installApk(Message msg) {
        File file = (File) msg.obj;
        install(file);
    }


    public void installApk() {
        String path = getSaveApkPath();
        if(null == path){
            return;
        }
        if("".equals(path) || path.isEmpty()){
            return;
        }
        File file = new File(path);
        if(!file.exists()){
            return;
        }
        install(file);
    }

    /**
     * 安装应用
     * */
    private void install(File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    /**
     * 保存已下载安装包的一些信息
     * */
    private void saveApkInfo(String path ,int version){
        SharedPreferences preferences = mContext.getSharedPreferences(PREFERENCES_APK_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCES_PATH, path);
        editor.putInt(PREFERENCES_VERSION, version);
        editor.commit();
    }

    /**
     * 获取已下载安装包的路径
     * */
    private String getSaveApkPath(){
        SharedPreferences preferences = mContext.getSharedPreferences(PREFERENCES_APK_NAME, Activity.MODE_PRIVATE);
        String path = preferences.getString(PREFERENCES_PATH, "");
        return path;
    }

    /**
     * 获取已下载安装包的版本
     * */
    private int getSaveApkVersion(){
        SharedPreferences preferences = mContext.getSharedPreferences(PREFERENCES_APK_NAME, Activity.MODE_PRIVATE);
        int version = preferences.getInt(PREFERENCES_VERSION, -1);
        return version;
    }

    /**
     * 判断本地安装包是否存在
     * */
    public boolean isApkExsit(){
        String path = getSaveApkPath();
        if(null == path){
            return false;
        }
        if("".equals(path) || path.isEmpty()){
            return false;
        }
        File file = new File(path);
        if(file.exists()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除本地安装包
     * */
    public void deleteLocalApk(){
        String path = getSaveApkPath();
        if(null == path){
            return;
        }
        if("".equals(path) || path.isEmpty()){
            return;
        }
        File file = new File(path);
        if(file.exists()){
            file.delete();
            file = null;
        }
    }



    /**
     * 声明一个handler来跟进进度条
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_ERROR:
                    ToastUtil.setToast("下载文件失败");
                    break;
                case DOWNLOAD_SUCCESS:

                    File file = (File) msg.obj;
                    if(null == file){
                        break;
                    }
                    String path = file.getAbsolutePath();

                    int version =1; //data.getVersion_code();
                    String[] arr=null;//data.getUpdate_infor();
                    String ret="";
                    if(arr!=null && arr.length>0)
                    {
                        for(int i=0;i<arr.length;i++)
                        {
                            ret +=arr[i]+"\n";
                        }
                    }
                    saveApkInfo(path, version);
                    //showUpdateDialog(ret);
                    break;
                default:
                    break;
            }
        };
    };



    private static final int NOTIFICATION_ID = 0x12;
    private Notification notification = null;
    private NotificationManager manager = null;

    @SuppressWarnings("deprecation")
    public void showNotifacation() {

        Intent intent = new Intent();
        intent.setClassName(mContext, "com.aiju.yhk.MainActivity");

        notification = new Notification(R.mipmap.logo, update_tip,System.currentTimeMillis());
        notification.contentView = new RemoteViews(mContext.getPackageName(),R.layout.notify_content);
        notification.contentView.setProgressBar(R.id.progressBar1, 100, 0,false);
        notification.contentView.setTextViewText(R.id.textView1, "下载中...");
        notification.contentView.setTextViewText(R.id.textView2, update_tip);
        notification.contentIntent = PendingIntent.getActivity(mContext, 0,intent, 0);
        manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }

    int press=100;
    public Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(press==100){
                setpress2(msg.arg1, msg.arg2);
                manager.notify(NOTIFICATION_ID, notification);
                press=0;
            }else{
                press++;
            }

            if (msg.arg2 == msg.arg1 && msg.arg1 != 0) {
                setpress2(msg.arg1, msg.arg2);
                Util.putPreferenceBoolean(BaseApplication.getInstance().getContext(), VERSION_KEY,false);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                File file = new File((String) msg.obj);
                //   intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

                if(Build.VERSION.SDK_INT>=24) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Uri apkURI = FileProvider.getUriForFile(mContext, "com.aiju.yhk.fileprovider", file);
                    intent.setDataAndType(apkURI, "application/vnd.android.package-archive");
                }else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }



                mContext.startActivity(intent);
                manager.cancel(NOTIFICATION_ID);
            }
        }

        public void setpress2(int press,int max) {
            double percent = press*100/max;

            int p=(int) (((double)press)/1024/1024*100);//ji
            int m=(int) (((double)max)/1024/1024*100);
            notification.contentView.setProgressBar(R.id.progressBar1, max, press, false);
            notification.contentView.setTextViewText(R.id.textView1,((double)p)/100+"M/"+((double)m)/100+"M");
        }
    };


    private Handler handler3 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            pd.setpress(msg.arg1, msg.arg2);
            if (msg.arg2 == msg.arg1 && msg.arg1 != 0) {
                pd.dismiss();
                Util.putPreferenceBoolean(BaseApplication.getInstance().getContext(),VERSION_KEY,false);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File file = new File((String) msg.obj);
                intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                mContext.startActivity(intent);
            }
        };
    };

    /**
     * 提示非强制更新时间每隔12个小时提示一次
     * @return
     */
    private boolean isShowUpdateTimeEnough(){
        long lastShow = DataManager.getInstance().getSystemSettingManager().getLastShowUpdate();
        long twelveHours = TimeUtils.getMillsByHour(12);
        if(System.currentTimeMillis() - lastShow > twelveHours){
            DataManager.getInstance().getSystemSettingManager().setLastShowUpdate(System.currentTimeMillis());
            return true;
        }
        return false;
    }



    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void install(Context context) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),app_name);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.aiju.yhk.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    private SystemUpdateDialog pd;
    private int updateType = 0;  //0  状态  1 当前页面

    /**
     * 这个接口主要用来监听安装包下载状态，以通知主页面更新显示。
     * */
    public interface IDownStatus{
        void downloadSuccess(String verinfo);

        void downloadFailed();

        void downResult(String ret);
    }


    /**
     *
     * 请求数据状态
     */
    public interface  IRequestDataStatus
    {
        void  requestSuccess(TaokeUpdateBean info);
        void  requestFail();
    }

}
