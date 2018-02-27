package com.aiju.zyb.bean;

import java.util.Date;

/**
 * Created by john on 2018/2/24.
 */

public class OccupationReplyInfo {
    private  int replyId;
    private String replyContent;
    private int replyUid;
    private String replyTime;
    private int replyNum;
    private int replyToUid;
    private int com_id;
    private UserInfoBean userInfo;


    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(int replyUid) {
        this.replyUid = replyUid;
    }



    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getReplyToUid() {
        return replyToUid;
    }

    public void setReplyToUid(int replyToUid) {
        this.replyToUid = replyToUid;
    }

    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }
    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

}
