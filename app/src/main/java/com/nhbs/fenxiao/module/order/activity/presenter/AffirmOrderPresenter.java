package com.nhbs.fenxiao.module.order.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.http.subscriber.NoTipRequestSubscriber;
import com.nhbs.fenxiao.module.order.bean.CreateOrderParams;
import com.nhbs.fenxiao.module.order.bean.CreateUserOrderBean;
import com.nhbs.fenxiao.module.order.bean.FirstAddressBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.utils.RxSchedulerUtils;

@SuppressLint("CheckResult")
public class AffirmOrderPresenter extends BaseViewPresenter<AffirmOrderViewer> {

    public AffirmOrderPresenter(AffirmOrderViewer viewer) {
        super(viewer);
    }

    public void createUserOrder(CreateOrderParams params) {
        XHttp.custom(OtherApiServices.class)
                .createUserOrder(HttpUtils.getJsonRequestBody(params))
                .compose(RxSchedulerUtils._io_main_o())
                .subscribeWith(new LoadingRequestSubscriber<CreateUserOrderBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(CreateUserOrderBean createUserOrderBean) {
                        assert getViewer() != null;
                        getViewer().createUserOrderSuccess(createUserOrderBean);
                    }
                });

    }

    public void getFirstAddress() {
        XHttpProxy.proxy(OtherApiServices.class)
                .getFirstAddress()
                .subscribeWith(new NoTipRequestSubscriber<FirstAddressBean>() {
                    @Override
                    protected void onSuccess(FirstAddressBean firstAddressBean) {
                        assert getViewer() != null;
                        getViewer().getFirstAddress(firstAddressBean);
                    }
                });
    }
}