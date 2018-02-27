package com.aiju.zyb.bean;

import java.io.Serializable;

/**
 * Created by john on 2018/2/5.
 */

public class OccupationTypeBean implements Serializable {
    private int sortId;
    private int sortType;
    private String sortName;
    private String sortDesc;

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
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

    public String getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }
}
