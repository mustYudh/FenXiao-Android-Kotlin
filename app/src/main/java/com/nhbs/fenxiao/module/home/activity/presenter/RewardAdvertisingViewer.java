package com.nhbs.fenxiao.module.home.activity.presenter;

import com.nhbs.fenxiao.module.home.bean.AdvertisingTypeBean;
import com.yu.common.mvp.Viewer;


public interface RewardAdvertisingViewer extends Viewer {
    void getAdvertisingTypeSuccess(AdvertisingTypeBean advertisingTypeBean);
}
