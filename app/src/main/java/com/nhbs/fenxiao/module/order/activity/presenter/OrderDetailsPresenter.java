package com.nhbs.fenxiao.module.order.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.order.bean.OrderDetailsBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class OrderDetailsPresenter extends BaseViewPresenter<OrderDetailsViewer> {

    public OrderDetailsPresenter(OrderDetailsViewer viewer) {
        super(viewer);
    }

    public void getOrderInfoDetail(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .orderInfoDetail(id)
                .subscribeWith(new LoadingRequestSubscriber<OrderDetailsBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(OrderDetailsBean orderDetailsBean) {
                        assert getViewer() != null;
                        getViewer().getOrderInfoDetailSuccess(orderDetailsBean);
                    }
                });
    }
}