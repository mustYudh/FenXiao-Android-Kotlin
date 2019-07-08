
package com.nhbs.fenxiao.http.subscriber;

import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;

public abstract class NoTipRequestSubscriber<T> extends BaseSubscriber<T> {



    @Override
    protected void onError(ApiException apiException) {

    }

}
