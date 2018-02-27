package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by john on 2018/2/24.
 */

public class GlideUtils {
   // Glide.with(this).load(url).transform(new CornersTransform(this,50)).into(iv1);


    /**
     *
     * 圆角
     * @param context
     * @param url
     * @param imageview
     * @param radius
     */
    public static void  getRoundImg(final Context context, String url, final ImageView imageview,int radius)
    {
        Glide.with(context).load(url).transform(new CornersTransform(context,radius)).into(imageview);
    }


    /**
     *
     * 圆形
     * @param context
     * @param url
     * @param imageview
     */
    public static  void getCicleImg(final Context context, String url, final ImageView imageview)
    {
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(imageview);
    }





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


    public static void  getLocalimage()
    {

    }

    public static String getAssetGifUrl(String name)
    {
        return "file:///android_asset/" + name;
    }
}
