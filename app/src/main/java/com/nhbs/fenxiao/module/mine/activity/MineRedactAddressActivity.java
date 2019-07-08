package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineRedactAddressPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineRedactAddressViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineRedactAddressActivity extends BaseBarActivity implements MineRedactAddressViewer {

    @PresenterLifeCycle
    MineRedactAddressPresenter presenter = new MineRedactAddressPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_redact_address_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_redact_address_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("编辑收货地址");
    }
}
