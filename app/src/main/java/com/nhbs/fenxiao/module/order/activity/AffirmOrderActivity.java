package com.nhbs.fenxiao.module.order.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.order.activity.presenter.AffirmOrderPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.AffirmOrderViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * 确认订单
 */
public class AffirmOrderActivity extends BaseBarActivity implements AffirmOrderViewer {

    @PresenterLifeCycle
    AffirmOrderPresenter presenter = new AffirmOrderPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_affirm_order_view);
    }

    @Override
    protected void loadData() {

    }
}
