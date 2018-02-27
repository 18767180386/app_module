package com.aiju.zyb.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.my.baselibrary.utils.SettingUtil;

/**
 * Created by AIJU on 2017-06-05.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private ICallBackView iCallBackViewListener;
        private int whdth=0,height=0,location=0;
        private int textSize=0,color=0;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        public Builder setCallBackView(ICallBackView iCallBackViewListener)
        {
            this.iCallBackViewListener=iCallBackViewListener;
            return  this;
        }


        public  Builder setDialogInfo(int width,int height,int location,int textSize,int color)
        {
            this.whdth=width;
            this.height=height;
            this.location=location;
            this.textSize=textSize;
            this.color=color;
            return  this;
        }



        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.DialogPrompt);

            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);


            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // layout.getLayoutParams().width=SettingUtil.getDisplaywidthPixels();
            // layout.getLayoutParams().height=300;
            TextView txttitle=(TextView)layout.findViewById(R.id.title);

            if(location==1) {
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.BOTTOM);
                //   lp.x = 0; // 新位置X坐标
                //    lp.y = 0; // 新位置Y坐标
                //  lp.width =width ; // 宽度
                // lp.height = height; // 高度
                //lp.alpha = 0.7f; // 透明度

                //        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
                DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                lp.height = (int) (d.heightPixels * 0.8); // 高度设置为屏幕的0.6

                lp.width = SettingUtil.getDisplaywidthPixels();
                //  lp.height=300;//(SettingUtil.getDisplayheightPixels()/4)*3;
                //设置窗口宽度为充满全屏
                // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //设置窗口高度为包裹内容
                //  lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogWindow.setWindowAnimations(R.style.mypopwindow_anim_style); //设置窗口弹出动画
                dialogWindow.setAttributes(lp);
                layout.findViewById(R.id.dialog_buttom).setVisibility(View.GONE);
                layout.findViewById(R.id.title).setVisibility(View.GONE);
                layout.findViewById(R.id.dialog_layout).setBackgroundResource(R.drawable.bg_bombbox);
            }else if(location==0){
                layout.findViewById(R.id.dialog_layout).setBackgroundResource(R.drawable.bg_bombox_default);
                layout.findViewById(R.id.dialog_buttom).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.title).setVisibility(View.VISIBLE);
                txttitle.setTextColor(color);
                txttitle.setTextSize(SettingUtil.px2sp(context,textSize));
            } else if(location==2)
            {
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.BOTTOM);
                //   lp.x = 0; // 新位置X坐标
                //    lp.y = 0; // 新位置Y坐标
                //  lp.width =width ; // 宽度
                // lp.height = height; // 高度
                //lp.alpha = 0.7f; // 透明度

                //        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
                DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                lp.height = (int) (d.heightPixels * 0.8); // 高度设置为屏幕的0.6

                lp.width = SettingUtil.getDisplaywidthPixels();
                //  lp.height=300;//(SettingUtil.getDisplayheightPixels()/4)*3;
                //设置窗口宽度为充满全屏
                // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //设置窗口高度为包裹内容
                //  lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialogWindow.setWindowAnimations(R.style.mypopwindow_anim_style); //设置窗口弹出动画
                dialogWindow.setAttributes(lp);
                layout.findViewById(R.id.dialog_buttom).setVisibility(View.GONE);
                layout.findViewById(R.id.title).setVisibility(View.GONE);
                layout.findViewById(R.id.dialog_layout).setBackgroundResource(R.drawable.bg_bombbox);

            }else if(location==3)
            {
                // layout.findViewById(R.id.dialog_layout).setBackgroundResource(R.drawable.bg_bombox_default);
                layout.findViewById(R.id.dialog_buttom).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
                layout.findViewById(R.id.title).setVisibility(View.VISIBLE);
                // layout.findViewById(R.id.title).setVisibility(View.GONE);
                layout.findViewById(R.id.dialog_layout).setBackgroundResource(R.drawable.bg_bombbox);

            }else if(location==4)
            {
                layout.findViewById(R.id.dialog_layout).setBackgroundResource(R.drawable.bg_bombbox);
                layout.findViewById(R.id.dialog_buttom).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.title).setVisibility(View.VISIBLE);

            }

            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }

            dialog.setContentView(layout);

            if(iCallBackViewListener!=null)
            {
                iCallBackViewListener.callBackView(dialog,layout);
            }

            return dialog;
        }

    }


    public  interface  ICallBackView
    {
        void callBackView(DialogInterface dialog, View View);
    }


}
