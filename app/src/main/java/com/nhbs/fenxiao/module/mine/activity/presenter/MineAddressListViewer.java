package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.yu.common.mvp.Viewer;


public interface MineAddressListViewer extends Viewer {
    void getUserAddressSuccess(MineAddressBean mineAddressBean);
}
