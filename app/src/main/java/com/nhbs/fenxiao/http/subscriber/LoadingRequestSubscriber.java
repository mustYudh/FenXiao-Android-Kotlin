
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
        cancel = cancel;
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetLoadingDialog.showLoading(activity, cancel);
    }

    @Override
    protected void onError(ApiException apiException) {
        ToastUtils.show(apiException.getDisplayMessage());
        NetLoadingDialog.dismissLoading();
    }


    @Override
    public void onComplete() {
        super.onComplete();
        NetLoadingDialog.dismissLoading();
    }
}
