package com.nhbs.fenxiao.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.AdvertisingTypeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class RewardAdvertisingPresenter extends BaseViewPresenter<RewardAdvertisingViewer> {

    public RewardAdvertisingPresenter(RewardAdvertisingViewer viewer) {
        super(viewer);
    }

    public void getAdvertisingType() {
        XHttpProxy.proxy(OtherApiServices.class)
                .advertisingType()
                .subscribeWith(new TipRequestSubscriber<AdvertisingTypeBean>() {
                    @Override
                    protected void onSuccess(AdvertisingTypeBean advertisingTypeBean) {
                        assert getViewer() != null;
                        getViewer().getAdvertisingTypeSuccess(advertisingTypeBean);
                    }
                });
    }
}