package com.aiju.zyb.view.widget;

/**
 * Created by AIJU on 2017-05-29.
 */

public class BannerItem {
    private int leftImage;

    private String itemTitle;

    private String itemDetail;

    private int rightImage;

    private String rightText;

    public BannerItem(int leftImage, String itemTitle, String rightText) {
        this.leftImage = leftImage;
        this.itemTitle = itemTitle;
        this.rightText = rightText;
    }

    public BannerItem(int leftImage, String itemTitle, int rightImage) {
        this.leftImage = leftImage;
        this.itemTitle = itemTitle;
        this.rightImage = rightImage;
    }

    public BannerItem(int leftImage, String itemTitle, String itemDetail, int rightImage) {
        this.leftImage = leftImage;
        this.itemTitle = itemTitle;
        this.itemDetail = itemDetail;
        this.rightImage = rightImage;
    }


    public int getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(int leftImage) {
        this.leftImage = leftImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public int getRightImage() {
        return rightImage;
    }

    public void setRightImage(int rightImage) {
        this.rightImage = rightImage;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }
}
