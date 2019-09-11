package com.nhbs.fenxiao.module.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.activity.presenter.AwardDetailsPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.AwardDetailsViewer;
import com.nhbs.fenxiao.module.home.bean.AwardDetailsBean;
import com.yu.common.mvp.PresenterLifeCycle;


public class AwardDetailsActivity extends BaseBarActivity implements AwardDetailsViewer {

    @PresenterLifeCycle
    AwardDetailsPresenter mPresenter = new AwardDetailsPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_award_details_view);
    }

    @Override
    protected void loadData() {
        setTitle("奖品详情");
        Bundle bundle = getIntent().getExtras();
        String award_id = bundle.getString("AWARD_ID");
        mPresenter.activityShareDetail(award_id);
    }

    @Override
    public void activityShareDetailSuccess(AwardDetailsBean awardDetailsBean) {
        if (awardDetailsBean != null) {
            
        }
    }
}
