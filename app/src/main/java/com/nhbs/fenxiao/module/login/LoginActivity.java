package com.nhbs.fenxiao.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.login.presenter.LoginPresenter;
import com.nhbs.fenxiao.module.login.presenter.LoginViewer;
import com.yu.common.login.LoginRedirectHelper;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudenghao
 */
public class LoginActivity extends BaseBarActivity implements LoginViewer {

    public static boolean pFlag = false;
    @PresenterLifeCycle
    private LoginPresenter mPresenter = new LoginPresenter(this);

    public static Intent callRedirectOtherActionIntent(Context context, String targetOther, Bundle bundle) {
        return LoginRedirectHelper.setRedirectData(context, LoginActivity.class, bundle, "",
                targetOther);
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        pFlag = true;
        setContentView(R.layout.activity_login_view);
    }

    @Override
    protected void loadData() {
        setTitle("测试登录页");
    }

    @Override
    public Bundle getLoginExtraBundle() {
        return LoginRedirectHelper.getLoginExtraBundle(getActivity());

    }

    @Override
    public String getRedirectOtherAction() {
        return LoginRedirectHelper.getRedirectOtherAction(getActivity());
    }

    @Override
    public String getRedirectActivityClassName() {
        return LoginRedirectHelper.getRedirectActivityClassName(getActivity());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pFlag = false;
    }
}
