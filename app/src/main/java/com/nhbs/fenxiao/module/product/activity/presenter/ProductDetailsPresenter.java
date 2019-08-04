package com.nhbs.fenxiao.module.product.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;


public class ProductDetailsPresenter extends BaseViewPresenter<ProductDetailsViewer> {

    public ProductDetailsPresenter(ProductDetailsViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getMerchandiseDetail(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .merchandiseDetail(id)
                .subscribeWith(new LoadingRequestSubscriber<MerchandiseDetailBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MerchandiseDetailBean merchandiseDetailBean) {
                        assert getViewer() != null;
                        getViewer().getMerchandiseDetailSuccess(merchandiseDetailBean);
                    }
                });
    }
}