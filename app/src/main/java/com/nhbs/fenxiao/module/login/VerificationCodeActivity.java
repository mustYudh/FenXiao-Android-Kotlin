package com.nhbs.fenxiao.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.login.presenter.VerificationCodePresenter;
import com.nhbs.fenxiao.module.login.presenter.VerificationCodeViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class VerificationCodeActivity extends BaseBarActivity implements VerificationCodeViewer {

    @PresenterLifeCycle
    VerificationCodePresenter presenter = new VerificationCodePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_verification_code_view);
    }

    @Override
    protected void loadData() {

    }
}
