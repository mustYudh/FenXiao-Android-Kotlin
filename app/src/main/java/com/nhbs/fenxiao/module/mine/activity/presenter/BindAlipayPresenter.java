package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.ui.DelayClickTextView;

@SuppressLint("CheckResult")
public class BindAlipayPresenter extends BaseViewPresenter<BindAlipayViewer> {

    public BindAlipayPresenter(BindAlipayViewer viewer) {
        super(viewer);
    }

    public void sendVerCode(String mobile, RxCountDown countDown, DelayClickTextView textView) {
        XHttpProxy.proxy(OtherApiServices.class)
                .sendBondAliAccount(mobile)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().sendVerCodeSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        textView.setClickable(true);
                        super.onError(apiException);
                        countDown.stop();
                    }
                });
    }


    public void boundAliAccount(String code,String account) {
        XHttpProxy.proxy(OtherApiServices.class)
                .boundAliAccount(code,account)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().boundAliAccountSuccess();
                    }
                });
    }

}