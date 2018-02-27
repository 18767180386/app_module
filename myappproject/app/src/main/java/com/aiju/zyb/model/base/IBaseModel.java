package com.aiju.zyb.model.base;

/**
 * Created by AIJU on 2017-04-28.
 */

public interface IBaseModel {
    String BaseUrl="http://119.23.254.180:8080/myadmin/";//http://119.23.254.180:8080/mytaoke/";


    /**
     * 商品首页
     */
    String IndexProList="taoke/indexlist";


    String IndexAdList="taoke/indexadlist";


    /**
     * 商品分类
     */
    String ProSortList="taoke/prosort";

    /**
     * 指定类别商品
     */
    String ProListByType="taoke/protype";


    /**
     *
     * 商品搜索
     */
    String  proSearch="taoke/prosearch";


    /**
     *
     * 版本更新
     */
    String  versionUpdate="taoke/versionupdate";



    /********************************************职业宝*******************************************************/
    /**
     *
     * 获取职业类型type
     */
    String  getOccupationType="occupation/getOccupationTypeList";


    /**
     *
     * 获取职业信息
     */
    String getOccupationList="occupation/getOccupationList";


    /**
     *
     * 获取职业信息
     */
    String  getOccupationId="occupation/getOccupationById";


    /**
     *
     * 获取评论url
     */
    String getCommentUrl="occupation/getCommentList";



}
