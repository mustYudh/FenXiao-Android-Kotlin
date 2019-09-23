package com.nhbs.fenxiao.module.store.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.store.activity.presenter.RedactStorePresenter;
import com.nhbs.fenxiao.module.store.activity.presenter.RedactStoreViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class RedactStoreActivity extends BaseBarActivity implements RedactStoreViewer {

    @PresenterLifeCycle
    RedactStorePresenter presenter = new RedactStorePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_redact_store_view);
    }

    @Override
    protected void loadData() {

    }
}
