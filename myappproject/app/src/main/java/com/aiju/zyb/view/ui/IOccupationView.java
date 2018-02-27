package com.aiju.zyb.view.ui;

import com.aiju.zyb.bean.OccupationBean;
import com.aiju.zyb.bean.OccupationTypeBean;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.base.ResultPageList;

/**
 * Created by john on 2018/2/5.
 */

public interface IOccupationView extends IBaseOccupationView{
    void  getOccupationType(ResultPageList<OccupationTypeBean> o);
    void  getOccupationList(ResultPageList<OccupationBean> o);
}
