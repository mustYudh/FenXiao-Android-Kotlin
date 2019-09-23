package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.GetWithdrawInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;


public class MineWithdrawInfoPresenter extends BaseViewPresenter<MineWithdrawInfoViewer> {

    public MineWithdrawInfoPresenter(MineWithdrawInfoViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getWithdrawByKey(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .getWithdrawByKey(id)
                .subscribeWith(new LoadingRequestSubscriber<GetWithdrawInfoBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(GetWithdrawInfoBean getWithdrawInfoBean) {
                        assert getViewer() != null;
                        getViewer().getWithdrawByKeySuccess(getWithdrawInfoBean);
                    }
                });
    }
}