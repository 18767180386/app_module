package com.aiju.zyb.model;

import android.content.Context;

import com.my.baselibrary.net.HttpRequestCallback;

/**
 * Created by john on 2018/2/5.
 */

public interface IOccupationModel {
    /**
     *
     *
     * @param context
     * @param callBack
     */
    void getOccupationTypeList(Context context,HttpRequestCallback callBack);


    /**
     *
     *
     * @param context
     * @param type
     * @param current_page
     * @param page_size
     * @param callBack
     */
    void getOccupationList(Context context, String keyword, int type,int current_page, int page_size, HttpRequestCallback callBack);


    /**
     *
     *
     * @param context
     * @param occ_id
     * @param callBack
     */
    void getOccupationById(Context context,int occ_id,HttpRequestCallback callBack);


    /**
     *
     *
     * @param context
     * @param occ_id
     * @param callBack
     */
    void getCommentList(Context context,int occ_id,int type,int current_page, int page_size, HttpRequestCallback callBack);
}
