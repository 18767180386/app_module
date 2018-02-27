package com.my.baselibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.my.baselibrary.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AIJU on 2017-05-30.
 */

public class LoadingDialogTools {
      private static Dialog loadingDialog;

        public static void showWaittingDialog(Context context) {
            try {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                    loadingDialog = null;
                }

                loadingDialog = new Dialog(context, R.style.LodingDialogStyle);
                View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null, false);
                loadingDialog.setContentView(view);
                loadingDialog.setCanceledOnTouchOutside(true);
                Window window = loadingDialog.getWindow();
                window.setGravity(Gravity.CENTER_VERTICAL);
                TextView msg = (TextView) view.findViewById(R.id.id_tv_loadingmsg);
                msg.setText(context.getResources().getString(R.string.load_tip));



//            loadingDialog.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.request_loading_progressbar));
//            loadingDialog.setMessage("正在加载中...");
                loadingDialog.show();
                // 3秒后还未完成任务，则设置为可取消
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (loadingDialog != null)
                            loadingDialog.setCancelable(true);
                    }
                };
                Timer timer = new Timer(true);
                timer.schedule(task, 3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void closeWaittingDialog() {
            try {
                if (loadingDialog != null)
                    loadingDialog.dismiss();
                loadingDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}