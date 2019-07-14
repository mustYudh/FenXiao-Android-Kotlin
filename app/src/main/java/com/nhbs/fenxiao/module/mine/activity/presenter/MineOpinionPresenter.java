package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

import java.io.File;

@SuppressLint("CheckResult")
public class MineOpinionPresenter extends BaseViewPresenter<MineOpinionViewer> {

    public MineOpinionPresenter(MineOpinionViewer viewer) {
        super(viewer);
    }

    public void opinionAdd(String context, String mobile, String conUrl, String type) {
        if (TextUtils.isEmpty(context)) {
            ToastUtils.show("内容不能为空");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show("联系号码不能为空");
            return;
        }

        if (TextUtils.isEmpty(conUrl)) {
            ToastUtils.show("图片至少选择一张");
            return;
        }

        if (TextUtils.isEmpty(type)) {
            ToastUtils.show("请选择建议类型");
            return;
        }

        XHttpProxy.proxy(OtherApiServices.class)
                .opinionAdd(context, mobile, conUrl, type)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().opinionAddSuccess();
                    }
                });
    }

    public void uploadImg(File file) {
        XHttp.post("http://139.180.218.55:8060/api/upload")
                .uploadFile("MultipartFile", file, (bytesWritten, contentLength, done) -> {
                }).execute(UploadImgBean.class)
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