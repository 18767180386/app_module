package com.aiju.zyb.view.photo;

import java.io.Serializable;

/**
 * Created by john on 2018/2/8.
 */

public class SationPhotoEntity implements Serializable {

    private String imgurl;

    private int Pos;

    public int getPos() {
        return Pos;
    }

    public void setPos(int pos) {
        Pos = pos;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}