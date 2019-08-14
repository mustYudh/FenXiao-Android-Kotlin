package com.nhbs.fenxiao.module.home.activity.presenter;

import com.nhbs.fenxiao.module.home.bean.AdvertisingInfoBean;
import com.nhbs.fenxiao.module.home.bean.AdvertisingShareBean;
import com.yu.common.mvp.Viewer;


public interface AdvertisingShareViewer extends Viewer {
    void advertiseShareSuccess(AdvertisingShareBean advertisingShareBean);

    void getAdvertiseInfoSuccess(AdvertisingInfoBean advertisingInfoBean);
}
