package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawInfoPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawInfoViewer;
import com.nhbs.fenxiao.module.mine.bean.GetWithdrawInfoBean;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineWithdrawInfoActivity extends BaseBarActivity implements MineWithdrawInfoViewer {

    @PresenterLifeCycle
    MineWithdrawInfoPresenter mPresenter = new MineWithdrawInfoPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_withdraw_info_view);
    }

    @Override
    protected void loadData() {
        setTitle("提现详情");

    }

    @Override
    public void getWithdrawByKeySuccess(GetWithdrawInfoBean getWithdrawInfoBean) {
        if (getWithdrawInfoBean != null){

        }
    }
}
