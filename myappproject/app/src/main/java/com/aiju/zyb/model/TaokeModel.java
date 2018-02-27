package com.aiju.zyb.model;

import android.content.Context;

import com.aiju.zyb.model.base.BaseModel;
import com.my.baselibrary.net.HttpRequestCallback;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AIJU on 2017-04-28.
 */

public class TaokeModel extends BaseModel implements ITaokeModel {

    /**
     *
     * 获取首页数据
     * @param context
     * @param type
     * @param pageIndex
     * @param pageSize
     * @param callback
     */
    @Override
    public void getIndexList(Context context,int type, int pageIndex, int pageSize, HttpRequestCallback callback) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type",type+"");
        map.put("offset", pageIndex + "");
        map.put("limit", pageSize + "");
        sendGetRequest(context,IndexProList, map, callback);
    }



    @Override
    public void getIndexAdList(Context context,int type,HttpRequestCallback callback) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type",type+"");
        sendGetRequest(context,IndexAdList, map, callback);
    }


    @Override
    public   void getProSortList(Context context,HttpRequestCallback callBack)
    {
        sendGetRequest(context,ProSortList, null, callBack);
    }


    @Override
    public   void getProListByType(Context context,int type,int limit,HttpRequestCallback callBack)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type",type+"");
        map.put("limit", limit + "");
        sendGetRequest(context,ProListByType, map, callBack);
    }


    /**
     *
     * 商品搜索
     * @param context
     * @param keyword
     * @param type
     * @param offset
     * @param limit
     * @param callBack
     */
    @Override
    public   void  proSearchList(Context context,String keyword,int type,int offset,int limit,HttpRequestCallback callBack)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("keyword",keyword);
        map.put("type",type+"");
        map.put("offset",offset+"");
        map.put("limit", limit + "");
        sendGetRequest(context,proSearch, map, callBack);
    }


    /**
     * 版本更新
     * @param context
     * @param packageName
     * @param versionCode
     * @param callBack
     */
    @Override
   public   void  versionUpdate(Context context,String packageName,String versionCode,HttpRequestCallback callBack)
   {
       Map<String, Object> map = new LinkedHashMap<>();
       map.put("packageName",packageName);
       map.put("versionCode",versionCode);
       sendGetRequest(context,versionUpdate, map, callBack);
   }




}
