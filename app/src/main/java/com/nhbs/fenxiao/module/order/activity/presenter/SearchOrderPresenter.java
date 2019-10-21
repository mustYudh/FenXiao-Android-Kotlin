package com.nhbs.fenxiao.module.order.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.order.bean.SearchOrderBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class SearchOrderPresenter extends BaseViewPresenter<SearchOrderViewer> {

    public SearchOrderPresenter(SearchOrderViewer viewer) {
        super(viewer);
    }

    public void getSearchOrder(String type,String pageNum, String pageSize,String searchTtile) {
        XHttpProxy.proxy(OtherApiServices.class)
                .queryShopKeeperOrders(type,pageNum,pageSize,searchTtile)
                .subscribeWith(new LoadingRequestSubscriber<SearchOrderBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(SearchOrderBean searchOrderBean) {
                        assert getViewer() != null;
                        getViewer().getSearchOrderSuccess(searchOrderBean);
                    }
                });
    }
}