package com.nhbs.fenxiao.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.HomeFindActivtyListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeEventPresenter extends BaseViewPresenter<HomeEventViewer> {

    public HomeEventPresenter(HomeEventViewer viewer) {
        super(viewer);
    }

    public void getFindActivtyList(int pageNum, int pageSize) {
        XHttpProxy.proxy(OtherApiServices.class)
                .findActivtyList(pageNum, pageSize)
                .subscribeWith(new TipRequestSubscriber<HomeFindActivtyListBean>() {
                    @Override
                    protected void onSuccess(HomeFindActivtyListBean homeFindActivtyListBean) {
                        assert getViewer() != null;
                        getViewer().getFindActivtyListSuccess(homeFindActivtyListBean);
                    }
                });
    }

    public void insertActivty(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .insertActivty(id)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().insertActivtySuccess();
                    }
                });
    }
}