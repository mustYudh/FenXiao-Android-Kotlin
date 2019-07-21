
package com.nhbs.fenxiao.http.subscriber;

import android.app.Activity;
import com.nhbs.fenxiao.http.loading.NetLoadingDialog;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.toast.ToastUtils;

public abstract class LoadingRequestSubscriber<T> extends BaseSubscriber<T> {

    private boolean cancel = true;
    private Activity activity;

    public LoadingRequestSubscriber(Activity activity, boolean cancel) {
        this.activity = activity;
        this.cancel = cancel;
    }

    @Override
    protected void onStart() {
        NetLoadingDialog.showLoading(activity, cancel);
        super.onStart();
    }

    @Override
    protected void onError(ApiException apiException) {
        ToastUtils.show(apiException.getDisplayMessage());
        NetLoadingDialog.dismissLoading();
    }


    @Override
    public void onComplete() {
        NetLoadingDialog.dismissLoading();
        super.onComplete();
    }
}
