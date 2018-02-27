package com.aiju.zyb.model;

import android.content.Context;

import com.aiju.zyb.model.base.BaseModel;
import com.my.baselibrary.net.HttpRequestCallback;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by john on 2018/2/5.
 */

public class OccupationModel extends BaseModel implements IOccupationModel {
    /**
     *
     * 获取职业类型type
     * @param context
     * @param callBack
     */
    @Override
    public void getOccupationTypeList(Context context, HttpRequestCallback callBack)
    {
        sendPostRequest(context,getOccupationType, null, callBack);
    }


    /**
     *
     * 获取职业信息列表
     * @param context
     * @param keyword
     * @param type
     * @param current_page
     * @param page_size
     * @param callBack
     */
    @Override
    public   void getOccupationList(Context context,String keyword,int type,  int current_page, int page_size, HttpRequestCallback callBack)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("keyword",keyword);
        map.put("type",type+"");
        map.put("offset",current_page+"");
        map.put("limit", page_size + "");
        sendPostRequest(context,getOccupationList, map, callBack);
    }


    /**
     *
     * 详情
     * @param context
     * @param occ_id
     * @param callBack
     */
    @Override
    public   void getOccupationById(Context context,int occ_id,HttpRequestCallback callBack)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("occ_id", occ_id + "");
        sendPostRequest(context,getOccupationId, map, callBack);
    }


    /**
     *
     * 获取评论
     * @param context
     * @param occ_id
     * @param type
     * @param current_page
     * @param page_size
     * @param callBack
     */
    @Override
    public   void getCommentList(Context context,int occ_id,int type,int current_page, int page_size, HttpRequestCallback callBack)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("occ_id",occ_id);
        map.put("sort_type",type+"");
        map.put("offset",current_page+"");
        map.put("limit", page_size + "");
        sendPostRequest(context,getCommentUrl, map, callBack);
    }




}

