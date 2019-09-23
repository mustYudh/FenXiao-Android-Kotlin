package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;


public class MineWithdrawPresenter extends BaseViewPresenter<MineWithdrawViewer> {

    public MineWithdrawPresenter(MineWithdrawViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void createUserWithdraw(String withdrawalAmount, String payType) {
        XHttpProxy.proxy(OtherApiServices.class)
                .createUserWithdraw(withdrawalAmount,payType)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().createUserWithdrawSuccess();
                    }
                });
    }
}