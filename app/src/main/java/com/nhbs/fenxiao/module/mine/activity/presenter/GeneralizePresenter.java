package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class GeneralizePresenter extends BaseViewPresenter<GeneralizeViewer> {

    public GeneralizePresenter(GeneralizeViewer viewer) {
        super(viewer);
    }

    public void querySpreadLogsList(String pageNum, String pageSize) {
        XHttpProxy.proxy(OtherApiServices.class)
                .querySpreadLogsList(pageNum, pageSize)
                .subscribeWith(new LoadingRequestSubscriber<MineSpreadLogsListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MineSpreadLogsListBean spreadLogsListBean) {
                        assert getViewer() != null;
                        getViewer().querySpreadLogsListSuccess(spreadLogsListBean);
                    }
                });
    }
}