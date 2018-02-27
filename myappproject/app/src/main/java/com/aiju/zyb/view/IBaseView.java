package com.aiju.zyb.view;

import com.aiju.zyb.bean.base.Result;
import com.aiju.zyb.bean.base.ResultPageList;

/**
 * Created by AIJU on 2017-04-14.
 */

public interface IBaseView {
    /**
     * 显示提示信息
     *
     * @param msg
     */
    void showTip(String msg);

    /**
     * 成功加载结果（默认回调方法，一般在子类里自定义）
     *
     * @param result
     */
    void loadSuccess(Result result);

    /**
     * 返回List
     */
    void loadSuccess(ResultPageList resultPageList);

    /**
     * 失败加载结果（默认回调方法，一般在子类里自定义）
     *
     * @param errorMsg
     */
    void loadFailure(String errorMsg);

    /**
     * 显示加载对话框
     */
    void showLoadingDialog();

    /**
     * 关闭加载对话框
     */
    void closeLoadingDialog();

}
