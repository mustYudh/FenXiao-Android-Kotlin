package com.nhbs.fenxiao.module.product.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class ProductFragmentPresenter extends BaseViewPresenter<ProductFragmentViewer> {

    public ProductFragmentPresenter(ProductFragmentViewer viewer) {
        super(viewer);
    }

    public void getMerchandiseClass() {
        XHttpProxy.proxy(OtherApiServices.class)
                .merchandiseClass()
                .subscribeWith(new LoadingRequestSubscriber<MerchandiseClassBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MerchandiseClassBean merchandiseClassBean) {
                        assert getViewer() != null;
                        getViewer().getMerchandiseClassSuccess(merchandiseClassBean);
                    }
                });
    }
}