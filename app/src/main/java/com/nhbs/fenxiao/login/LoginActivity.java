package com.nhbs.fenxiao.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.login.presenter.LoginPresenter;
import com.nhbs.fenxiao.login.presenter.LoginViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudenghao
 */
public class LoginActivity extends BaseBarActivity implements LoginViewer {

  @PresenterLifeCycle LoginPresenter presenter = new LoginPresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_login_view);
  }

  @Override protected void loadData() {

  }
}
