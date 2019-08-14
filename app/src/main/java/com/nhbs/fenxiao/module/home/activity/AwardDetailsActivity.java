package com.nhbs.fenxiao.module.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.activity.presenter.AwardDetailsPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.AwardDetailsViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class AwardDetailsActivity extends BaseBarActivity implements AwardDetailsViewer {

    @PresenterLifeCycle
    AwardDetailsPresenter presenter = new AwardDetailsPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_award_details_view);
    }

    @Override
    protected void loadData() {

    }
}
