package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineWithdrawActivity extends BaseBarActivity implements MineWithdrawViewer {

    @PresenterLifeCycle
    MineWithdrawPresenter mPresenter = new MineWithdrawPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_withdraw_view);
    }

    @Override
    protected void loadData() {

    }
}
