package com.my.baselibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.my.baselibrary.base.BaseApplication;

/**
 * Created by AIJU on 2017-04-30.
 */

public class GlideTool {
    /***
     * 图片加载
     * @param context
     * @param url
     */
    public  static   void  glideImageLoad(final Context context, String url, final ImageView imageview)
    {
        Glide.with(context).load(url).crossFade().into(imageview);
        // Picasso.with(context).load(pic).placeholder(R.drawable.dsb_no_avatar).into(headimg);

        //Glide.with(context).load(url).dontAnimate().placeholder(R.drawable.ic_empty).into(imageview);



        /*
        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageview) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageview.setImageDrawable(circularBitmapDrawable);
            }
        });
        */
    }


    /**
     *
     * @param context
     * @param url
     * @param imageview
     * @param defaultImg
     */
    public  static  void  glideImageLoad(Context context,String url,ImageView imageview,int defaultImg)
    {
        //Glide.with(context).load(url).crossFade().into(imageview);
        // Picasso.with(context).load(pic).placeholder(R.drawable.dsb_no_avatar).into(headimg);

        Glide.with(context).load(url).dontAnimate().placeholder(defaultImg).into(imageview);
    }


    public  static  void  glideImageLoad(Context context,String url,ImageView imageview,int defaultImg,int defalutErrorImg)
    {
        //Glide.with(context).load(url).crossFade().into(imageview);
        // Picasso.with(context).load(pic).placeholder(R.drawable.dsb_no_avatar).into(headimg);

        Glide.with(context).load(url).dontAnimate().placeholder(defaultImg).error(defalutErrorImg).into(imageview);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCirclePic(final Context context,final ImageView imageView, String url, int errResId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(url).asBitmap().centerCrop().error(errResId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    public static void  getLocalimage()
    {

    }

    public static String getAssetGifUrl(String name)
    {
        return "file:///android_asset/" + name;
    }




    // 清除图片磁盘缓存，调用Glide自带方法
    public static   boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(BaseApplication.getInstance().getContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(BaseApplication.getInstance().getContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public static boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(BaseApplication.getInstance().getContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
