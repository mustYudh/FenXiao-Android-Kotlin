package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.yu.common.mvp.Viewer;


public interface MineRedactAddressViewer extends Viewer {
    void userAddressAddSuccess();

    void userAddressEditSuccess();

    void userAddressDelSuccess();
}
