package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;

import java.io.File;

@SuppressLint("CheckResult")
public class MineOpinionPresenter
        extends BaseViewPresenter<MineOpinionViewer> {

    public MineOpinionPresenter(MineOpinionViewer viewer) {
        super(viewer);
    }

    public void opinionAdd(String context, String mobile, String conUrl, String type) {
        XHttpProxy.proxy(OtherApiServices.class)
                .opinionAdd(context, mobile, conUrl, type)
                .subscribeWith(new TipRequestSubscriber<Object>(){
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().opinionAddSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().opinionAddFail();
                    }
                });
    }


    public void uploadImg(File file) {
        XHttp.post("/upload")
                .baseUrl("http://139.180.218.55:8070")
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