package com.nhbs.fenxiao.module.mine.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
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
}