package com.nhbs.fenxiao.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.login.presenter.LoginPresenter;
import com.nhbs.fenxiao.module.login.presenter.LoginViewer;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.login.LoginRedirectHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

/**
 * @author yudenghao
 */
public class LoginActivity extends BaseBarActivity implements LoginViewer, View.OnClickListener {

  public static boolean pFlag = false;
  @PresenterLifeCycle private LoginPresenter mPresenter = new LoginPresenter(this);

  public static Intent callRedirectOtherActionIntent(Context context, String targetOther,
      Bundle bundle) {
    return LoginRedirectHelper.setRedirectData(context, LoginActivity.class, bundle, "",
        targetOther);
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    pFlag = true;
    setContentView(R.layout.activity_login_view);
  }

  @Override protected void loadData() {
    initView();
    initListener();
  }

  private void initListener() {
    bindView(R.id.next_action, this);
    bindView(R.id.we_chat_login, this);
  }

  private void initView() {
    setBackImgRes(R.drawable.ic_cancel);
    bindView(R.id.divider).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
  }

  @Override public Bundle getLoginExtraBundle() {
    return LoginRedirectHelper.getLoginExtraBundle(getActivity());
  }

  @Override public String getRedirectOtherAction() {
    return LoginRedirectHelper.getRedirectOtherAction(getActivity());
  }

  @Override public String getRedirectActivityClassName() {
    return LoginRedirectHelper.getRedirectActivityClassName(getActivity());
  }

  @Override protected void onPause() {
    super.onPause();
    pFlag = false;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.next_action:
        try {
          XHttpProxy.proxy(AppApiServices.class)
              .sendSems("15968170723")
              .subscribe(new TipRequestSubscriber<Object>() {
                @Override protected void onSuccess(Object o) {
                    Log.e("=====>","成功");
                }
              });
        } catch (Exception e) {
          e.printStackTrace();
        }
        getLaunchHelper().startActivity(VerificationCodeActivity.class);
        break;
      case R.id.we_chat_login:
        ToastUtils.show("微信登录");
        break;
      default:
    }
  }
}
