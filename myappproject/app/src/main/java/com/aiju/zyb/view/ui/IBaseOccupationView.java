package com.aiju.zyb.view.ui;

/**
 * Created by john on 2018/2/5.
 */

public interface IBaseOccupationView {
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
