package com.nhbs.fenxiao.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.AdvertisingInfoBean;
import com.nhbs.fenxiao.module.home.bean.AdvertisingShareBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class AdvertisingSharePresenter extends BaseViewPresenter<AdvertisingShareViewer> {

    public AdvertisingSharePresenter(AdvertisingShareViewer viewer) {
        super(viewer);
    }

    public void advertiseShare(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .advertiseShare(id)
                .subscribeWith(new LoadingRequestSubscriber<AdvertisingShareBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(AdvertisingShareBean advertisingShareBean) {
                        assert getViewer() != null;
                        getViewer().advertiseShareSuccess(advertisingShareBean);
                    }
                });
    }

    public void getAdvertiseInfo(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .getAdvertiseInfo(id)
                .subscribeWith(new LoadingRequestSubscriber<AdvertisingInfoBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(AdvertisingInfoBean advertisingInfoBean) {
                        assert getViewer() != null;
                        getViewer().getAdvertiseInfoSuccess(advertisingInfoBean);
                    }
                });
    }
}