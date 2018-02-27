package com.aiju.zyb.bean;

import java.io.Serializable;

/**
 * Created by AIJU on 2017-05-06.
 */

public class TaokeProSort implements Serializable {
    private int id;
    private int sortType;
    private String sortName;
    private int sortDesc;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSortType() {
        return sortType;
    }
    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
    public String getSortName() {
        return sortName;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public int getSortDesc() {
        return sortDesc;
    }
    public void setSortDesc(int sortDesc) {
        this.sortDesc = sortDesc;
    }
}
