package com.aiju.zyb.view.widget;

import java.util.List;

/**
 * Created by AIJU on 2017-05-29.
 */

public class BannerGroup {
    public BannerGroup(String title, List<BannerItem> items){
        this.title=title;
        this.items=items;

    }

    private String title;

    private List<BannerItem> items;

    private boolean Detail;

    public boolean isDetail() {
        return Detail;
    }

    public void setDetail(boolean detail) {
        Detail = detail;
    }

    public List<BannerItem> getItems() {
        return items;
    }

    public void setItems(List<BannerItem> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
