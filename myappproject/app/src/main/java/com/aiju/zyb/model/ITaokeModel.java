package com.aiju.zyb.model;

import android.content.Context;

import com.my.baselibrary.net.HttpRequestCallback;

/**
 * Created by AIJU on 2017-04-28.
 */

public interface ITaokeModel {
    /**
     * 首页列表数据
     * @param context
     * @param type
     * @param current_page
     * @param page_size
     * @param callBack
     */
    void getIndexList(Context context, int type, int current_page, int page_size, HttpRequestCallback callBack);


    /**
     *
     * /首页广告数据
     * @param context
     * @param type
     * @param callBack
     */
    void getIndexAdList(Context context, int type,HttpRequestCallback callBack);


    /**
     * 商品分类
     * @param context
     * @param callBack
     */
    void getProSortList(Context context,HttpRequestCallback callBack);


    /**
     *
     * 获取指定类别商品
     * @param context
     * @param type
     * @param limit
     * @param callBack
     */
    void getProListByType(Context context,int type,int limit,HttpRequestCallback callBack);


    /**
     *
     * 商品搜搜
     * @param context
     * @param keyword
     * @param type
     * @param offset
     * @param limit
     * @param callBack
     */
    void  proSearchList(Context context,String keyword,int type,int offset,int limit,HttpRequestCallback callBack);


    /**
     *
     * 版本更新
     * @param context
     * @param packageName
     * @param versionCode
     * @param callBack
     */
    void  versionUpdate(Context context,String packageName,String versionCode,HttpRequestCallback callBack);
}
