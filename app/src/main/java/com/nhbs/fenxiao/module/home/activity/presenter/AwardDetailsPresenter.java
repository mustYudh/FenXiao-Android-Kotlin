package com.nhbs.fenxiao.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.AwardDetailsBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class AwardDetailsPresenter extends BaseViewPresenter<AwardDetailsViewer> {

    public AwardDetailsPresenter(AwardDetailsViewer viewer) {
        super(viewer);
    }

    public void activityShareDetail(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .activityShareDetail(id)
                .subscribeWith(new LoadingRequestSubscriber<AwardDetailsBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(AwardDetailsBean awardDetailsBean) {
                        assert getViewer() != null;
                        getViewer().activityShareDetailSuccess(awardDetailsBean);
                    }
                });
    }
}