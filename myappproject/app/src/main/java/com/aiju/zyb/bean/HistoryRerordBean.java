package com.aiju.zyb.bean;

import java.io.Serializable;

/**
 * Created by AIJU on 2017-05-21.
 */

public class HistoryRerordBean implements Serializable {
    private int id;
    private String keyword;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
