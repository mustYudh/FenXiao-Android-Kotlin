package com.nhbs.fenxiao.module.store.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;

import java.io.File;

@SuppressLint("CheckResult")
public class RedactStorePresenter extends BaseViewPresenter<RedactStoreViewer> {

    public RedactStorePresenter(RedactStoreViewer viewer) {
        super(viewer);
    }

    public void userShopUpdate(String id, String shopImage, String shopName, String describes) {
        XHttpProxy.proxy(OtherApiServices.class)
                .userShopUpdate(id, shopImage, shopName, describes)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().userShopUpdateSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().userShopUpdateFail();
                    }
                });
    }

    public void uploadImg(File file) {
        XHttp.post("/upload")
                .baseUrl("http://bapi.novobus.cn")
                .subUrl("/api")
                .uploadFile("file", file, (bytesWritten, contentLength, done) -> {
                })
                .execute(UploadImgBean.class)
                .compose(RxLifecycle.with(getActivity()).bindToLifecycle())
                .subscribeWith(new ProgressLoadingSubscriber<UploadImgBean>() {
                    @Override
                    public void onSuccess(UploadImgBean uploadImgBean) {
                        assert getViewer() != null;
                        getViewer().uploadImgSuccess(uploadImgBean);
                    }

                    @Override
                    public void onError(ApiException e) {
                        assert getViewer() != null;
                        getViewer().uploadImgFail();
                    }
                });
    }
}