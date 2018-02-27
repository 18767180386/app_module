package com.aiju.zyb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AIJU on 2017-04-30.
 */

public class TaokeItemBean implements Serializable {
    private int id;
    private String nick;
    private String volume;
    private int user_type;
    private String provcity;
    private String zk_final_price;
    private String item_url;
    private String num_iid;
    private String pict_url;
    private String reserve_price;
    private String title;
    private String seller_id;

    public TaokeImageBean getSmall_images() {
        return small_images;
    }

    public void setSmall_images(TaokeImageBean small_images) {
        this.small_images = small_images;
    }

    private TaokeImageBean small_images;
    private int pro_cate_type;

    private List<TaokeProImg> taokeimg;

    public List<TaokeProImg> getTaokeimg() {
        return taokeimg;
    }
    public void setTaokeimg(List<TaokeProImg> taokeimg) {
        this.taokeimg = taokeimg;
    }

    public int getPro_cate_type() {
        return pro_cate_type;
    }
    public void setPro_cate_type(int pro_cate_type) {
        this.pro_cate_type = pro_cate_type;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getVolume() {
        return volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public int getUser_type() {
        return user_type;
    }
    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
    public String getProvcity() {
        return provcity;
    }
    public void setProvcity(String provcity) {
        this.provcity = provcity;
    }
    public String getZk_final_price() {
        return zk_final_price;
    }
    public void setZk_final_price(String zk_final_price) {
        this.zk_final_price = zk_final_price;
    }
    public String getItem_url() {
        return item_url;
    }
    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }
    public String getNum_iid() {
        return num_iid;
    }
    public void setNum_iid(String num_iid) {
        this.num_iid = num_iid;
    }
    public String getPict_url() {
        return pict_url;
    }
    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }
    public String getReserve_price() {
        return reserve_price;
    }
    public void setReserve_price(String reserve_price) {
        this.reserve_price = reserve_price;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSeller_id() {
        return seller_id;
    }
    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
