package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.mine.bean.MineGroupBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineTeamActivityPresenter extends BaseViewPresenter<MineTeamActivityViewer> {

    public MineTeamActivityPresenter(MineTeamActivityViewer viewer) {
        super(viewer);
    }

    public void mineGroup(String pageNum, String pageSize) {
        XHttpProxy.proxy(OtherApiServices.class)
                .mineGroup(pageNum, pageSize)
                .subscribeWith(new LoadingRequestSubscriber<MineGroupBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MineGroupBean mineGroupBean) {
                        assert getViewer() != null;
                        getViewer().mineGroupSuccess(mineGroupBean);
                    }
                });
    }
}