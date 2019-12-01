package com.nhbs.fenxiao.module.order.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class RefundApplyPresenter extends BaseViewPresenter<RefundApplyViewer> {

    public RefundApplyPresenter(RefundApplyViewer viewer) {
        super(viewer);
    }

    public void applyRefund(String orderId,String remark,String reason,String imageUrls,int isGoods,int goodsStatus) {
        XHttpProxy.proxy(OtherApiServices.class)
                .applyRefund(orderId, remark, reason, imageUrls ,isGoods, goodsStatus)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object object) {
                        assert getViewer() != null;
                        getViewer().applySuc();
                    }
                });
    }

}