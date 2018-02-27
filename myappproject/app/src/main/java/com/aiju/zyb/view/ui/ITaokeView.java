package com.aiju.zyb.view.ui;

import com.aiju.zyb.bean.TaokeIndexAdBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;

/**
 * Created by AIJU on 2017-04-28.
 */

public interface ITaokeView  extends  IConmenView {

    void getIndextList(ResultPageList<TaokeItemBean> result);

    void getIndexAdList(Result<TaokeIndexAdBean> reuslt);

    void getProSortList(ResultPageList<TaokeProSort> result);
}
