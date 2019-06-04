package com.nhbs.fenxiao.module.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseActivity;
import com.nhbs.fenxiao.module.splash.presenter.SplashPresenter;
import com.nhbs.fenxiao.module.splash.presenter.SplashViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public class SplashActivity extends BaseActivity implements SplashViewer {
    
    @PresenterLifeCycle
    private SplashPresenter mPresenter = new SplashPresenter(this);
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
            mPresenter.handleCountDown();
    }

    @Override
    protected void loadData() {

    }
}
