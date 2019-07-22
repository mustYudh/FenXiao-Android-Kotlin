package com.nhbs.fenxiao.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeProductClassifyPresenter extends BaseViewPresenter<HomeProductClassifyViewer> {

    public HomeProductClassifyPresenter(HomeProductClassifyViewer viewer) {
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