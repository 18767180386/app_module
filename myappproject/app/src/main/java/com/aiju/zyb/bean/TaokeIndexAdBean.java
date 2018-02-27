package com.aiju.zyb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AIJU on 2017-05-02.
 */

public class TaokeIndexAdBean  implements Serializable{
    private List<TaokeItemBean> indexsecondList;
    private List<TaokeItemBean>  carousellsit;

    public List<TaokeItemBean> getIndexsecondList() {
        return indexsecondList;
    }

    public void setIndexsecondList(List<TaokeItemBean> indexsecondList) {
        this.indexsecondList = indexsecondList;
    }

    public List<TaokeItemBean> getCarousellsit() {
        return carousellsit;
    }

    public void setCarousellsit(List<TaokeItemBean> carousellsit) {
        this.carousellsit = carousellsit;
    }



}
