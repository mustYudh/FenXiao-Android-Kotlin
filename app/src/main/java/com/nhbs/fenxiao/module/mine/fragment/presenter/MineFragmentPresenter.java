package com.nhbs.fenxiao.module.mine.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;


public class MineFragmentPresenter extends BaseViewPresenter<MineFragmentViewer> {

    public MineFragmentPresenter(MineFragmentViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getUserInfo() {
        XHttpProxy.proxy(OtherApiServices.class)
                .userInfo()
                .subscribeWith(new LoadingRequestSubscriber<MineUserInfoBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MineUserInfoBean mineUserInfoBean) {
                        assert getViewer() != null;
                        getViewer().getUserInfoSuccess(mineUserInfoBean);
                    }
                });
    }
}