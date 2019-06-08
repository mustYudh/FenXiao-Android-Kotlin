package com.nhbs.fenxiao.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.login.presenter.BindPhonePresenter;
import com.nhbs.fenxiao.module.login.presenter.BindPhoneViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class BindPhoneActivity extends BaseBarActivity implements BindPhoneViewer {

    @PresenterLifeCycle
    BindPhonePresenter presenter = new BindPhonePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_phone_view);
    }

    @Override
    protected void loadData() {

    }
}
