
package com.nhbs.fenxiao.http.subscriber;

import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.toast.ToastUtils;

public abstract class TipRequestSubscriber<T> extends BaseSubscriber<T> {


    public TipRequestSubscriber() {

    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onError(ApiException apiException) {
            ToastUtils.show(apiException.getDisplayMessage());
    }

}
