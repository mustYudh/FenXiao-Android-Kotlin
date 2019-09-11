package com.nhbs.fenxiao.module.mine.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineOrderListFragmentPresenter extends BaseViewPresenter<MineOrderListFragmentViewer> {

    public MineOrderListFragmentPresenter(MineOrderListFragmentViewer viewer) {
        super(viewer);
    }

    public void getMineOrder(String pageNum, String pageSize, String type, String status) {
        XHttpProxy.proxy(OtherApiServices.class)
                .queryMyOrders(pageNum, pageSize, type, status)
                .subscribeWith(new LoadingRequestSubscriber<MineOrderListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MineOrderListBean mineOrderListBean) {
                        assert getViewer() != null;
                        getViewer().getMineOrderSuccess(mineOrderListBean);
                    }
                });
    }

    public void confirmGoods(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .confirmGoods(id)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().confirmGoodsSuccess();
                    }
                });
    }

    public void userToPay(String orderId, String type, String payWay) {
        XHttpProxy.proxy(OtherApiServices.class)
                .userToPay(orderId, type, payWay)
                .subscribeWith(new TipRequestSubscriber<PayInfo>() {
                    @Override
                    protected void onSuccess(PayInfo payInfo) {
                        assert getViewer() != null;
                        getViewer().userToPaySuccess(payInfo);
                    }
                });
    }

    public void cancelOrder(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .cancelOrder(id)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().cancelOrderSuccess();
                    }
                });
    }
}