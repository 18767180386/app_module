package com.aiju.zyb.view.ui.uiview;

import com.aiju.zyb.bean.OccupationBean;
import com.aiju.zyb.bean.OccupationCommentInfo;
import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.view.ui.IBaseOccupationView;

/**
 * Created by john on 2018/2/24.
 */

public interface IOuccpationDetailView extends IBaseOccupationView  {
     void  getOccupationView(Result<OccupationBean> o);
     void  getCommentView(ResultPageList<OccupationCommentInfo> o);
}
