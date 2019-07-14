package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineAddressListPresenter extends BaseViewPresenter<MineAddressListViewer> {

    public MineAddressListPresenter(MineAddressListViewer viewer) {
        super(viewer);
    }

    public void getUserAddress() {
        XHttpProxy.proxy(OtherApiServices.class)
                .getUserAddress()
                .subscribeWith(new LoadingRequestSubscriber<MineAddressBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MineAddressBean mineAddressBean) {
                        assert getViewer() != null;
                        getViewer().getUserAddressSuccess(mineAddressBean);
                    }
                });
    }
}