package com.aiju.zyb.view.ui;

import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.base.ResultPageList;

/**
 * Created by AIJU on 2017-05-21.
 */

public interface  IProListView  extends  IConmenView{
    void getProList(ResultPageList<TaokeItemBean> result);
}
