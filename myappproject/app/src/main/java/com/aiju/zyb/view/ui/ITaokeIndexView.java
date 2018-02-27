package com.aiju.zyb.view.ui;

import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.base.ResultPageList;

/**
 * Created by AIJU on 2017-05-01.
 */

public interface ITaokeIndexView {
    void getIndextList(ResultPageList<TaokeItemBean> result);
}
