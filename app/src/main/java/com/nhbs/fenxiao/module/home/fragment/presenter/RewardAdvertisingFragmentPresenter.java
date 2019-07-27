package com.nhbs.fenxiao.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.HomeFindAdvertisingListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class RewardAdvertisingFragmentPresenter extends BaseViewPresenter<RewardAdvertisingFragmentViewer> {

    public RewardAdvertisingFragmentPresenter(RewardAdvertisingFragmentViewer viewer) {
        super(viewer);
    }

    public void getFindAdvertisingList(String type, int pageNum, int pageSize) {
        XHttpProxy.proxy(OtherApiServices.class)
                .findAdvertisingList(type, pageNum, pageSize)
                .subscribeWith(new LoadingRequestSubscriber<HomeFindAdvertisingListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(HomeFindAdvertisingListBean homeFindAdvertisingListBean) {
                        assert getViewer() != null;
                        getViewer().getFindAdvertisingListSuccess(homeFindAdvertisingListBean);
                    }
                });
    }
}