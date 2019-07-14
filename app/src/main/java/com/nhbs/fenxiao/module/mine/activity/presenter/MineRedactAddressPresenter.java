package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult")
public class MineRedactAddressPresenter extends BaseViewPresenter<MineRedactAddressViewer> {

    public MineRedactAddressPresenter(MineRedactAddressViewer viewer) {
        super(viewer);
    }

    public void userAddressAdd(String userName, String mobile, String address, String specificAddress, int type) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.show("收货人名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show("收货人手机号不能为空");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            ToastUtils.show("收货人地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(specificAddress)) {
            ToastUtils.show("收货人详细地址不能为空");
            return;
        }

        XHttpProxy.proxy(OtherApiServices.class)
                .userAddressAdd(userName, mobile, address, specificAddress, type)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().userAddressAddSuccess();
                    }
                });
    }

    public void userAddressEdit(String userName, String mobile, String address, String specificAddress, String id, int type) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.show("收货人名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show("收货人手机号不能为空");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            ToastUtils.show("收货人地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(specificAddress)) {
            ToastUtils.show("收货人详细地址不能为空");
            return;
        }
        XHttpProxy.proxy(OtherApiServices.class)
                .userAddressEdit(userName, mobile, address, specificAddress, id, type)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().userAddressEditSuccess();
                    }
                });
    }

    public void userAddressDel(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .userAddressDel(id)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().userAddressDelSuccess();
                    }
                });
    }
}