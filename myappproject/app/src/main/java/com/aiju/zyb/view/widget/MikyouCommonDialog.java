package com.aiju.zyb.view.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by AIJU on 2017-06-05.
 */

public class MikyouCommonDialog {
    private Context context;
    private int customeLayoutId;
    private String dialogTitle;
    private String dialogMessage;
    private String positiveText;
    private String negativeText;

    private View dialogView;
    private OnDialogListener listener;
    //带有自定义view的构造器
    public MikyouCommonDialog(Context context,int customeLayoutId, String dialogTitle, String positiveText, String negativeText) {
        this.context = context;
        this.customeLayoutId = customeLayoutId;
        this.dialogTitle = dialogTitle;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.dialogView=View.inflate(context,customeLayoutId,null);
    }
    //不带自定义view的构造器
    public MikyouCommonDialog(Context context, String dialogMessage, String dialogTitle, String positiveText, String negativeText) {
        this.context = context;
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    public View getDialogView() {
        return dialogView;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    public  CustomDialog.Builder dialog=null;

    public void showDialog(){
        dialog=new CustomDialog.Builder(context);
        dialog.setTitle(dialogTitle);//设置标题
        //注意:dialogMessage和dialogView是互斥关系也就是dialogMessage存在dialogView就不存在,dialogView不存在dialogMessage就存在
        if (dialogMessage!=null){
            dialog.setMessage(dialogMessage);//设置对话框内容
        }else{
            dialog.setContentView(dialogView);//设置对话框的自定义view对象

        }
        dialog.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                if (listener!=null){
                    listener.dialogPositiveListener(dialogView,dialogInterface,which);
                }
            }
        }).setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                if (listener!=null){
                    listener.dialogNegativeListener(dialogView,dialogInterface,which);
                }
            }
        }).setCallBackView(new CustomDialog.ICallBackView() {
            @Override
            public void callBackView(DialogInterface dialogInterface,View view) {
                //TextView txt=(TextView)view.findViewById(R.id.tip_one);
                // UtilToast.show(txt.getText().toString());
                if(iMainCallBackViewListenter!=null)
                {
                    iMainCallBackViewListenter.callBackDialogView(dialogInterface,view);
                }
            }
        }).setDialogInfo(width,height,location,textSize,textColor).create().show();
    }
    //注册监听器方法
    public MikyouCommonDialog setOnDiaLogListener(OnDialogListener listener){
        this.listener=listener;
        return this;//把当前对象返回,用于链式编程
    }
    //定义一个监听器接口
    public interface OnDialogListener{
        //customView　这个参数需要注意就是如果没有自定义view,那么它则为null
        void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which);
        void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which);
    }

    public  IMainCallBackView iMainCallBackViewListenter;
    public MikyouCommonDialog setDialogViewListener(IMainCallBackView iMainCallBackView)
    {
        this.iMainCallBackViewListenter=iMainCallBackView;
        return this;
    }

    public interface  IMainCallBackView{
        void  callBackDialogView(DialogInterface dialogInterface, View view);
    }


    private  int width=0,height=0,location=0,textSize=0,textColor=0;

    public  MikyouCommonDialog setDialogInfo(int width,int height,int location,int textSize,int textColor)
    {
        this.width=width;
        this.height=height;
        this.location=location;
        this.textSize=textSize;
        this.textColor=textColor;
        return  this;
    }
}
