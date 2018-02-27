package com.aiju.zyb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 2018/2/6.
 */

public class OccupationBean implements Serializable{
    private int id;
    private int userId;
    private String title;
    private String author;
    private String content;
    private int commentnum;
    private String createtime;
    private int msgType;
    private List<OccupationPicBean> occupationPicInfos;

    private UserInfoBean userInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public List<OccupationPicBean> getOccupationPicInfos() {
        return occupationPicInfos;
    }

    public void setOccupationPicInfos(List<OccupationPicBean> occupationPicInfos) {
        this.occupationPicInfos = occupationPicInfos;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }
}
