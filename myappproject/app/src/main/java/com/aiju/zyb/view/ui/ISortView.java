package com.aiju.zyb.view.ui;

import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.ResultPageList;

/**
 * Created by AIJU on 2017-05-06.
 */

public interface ISortView extends IConmenView {
    void getProSortList(ResultPageList<TaokeProSort> result);

    void getProListByType(ResultPageList<TaokeItemBean> result);
}
