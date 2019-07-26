package com.nhbs.fenxiao.module.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.login.presenter.LoginPresenter;
import com.nhbs.fenxiao.module.login.presenter.LoginViewer;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.login.LoginRedirectHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.share.AuthLoginHelp;
import com.yu.share.callback.AuthLoginCallback;
import java.util.Map;

/**
 * @author yudenghao
 */
public class LoginActivity extends BaseBarActivity
    implements LoginViewer, View.OnClickListener, AuthLoginCallback {

  public static boolean pFlag = false;
  @PresenterLifeCycle private LoginPresenter mPresenter = new LoginPresenter(this);
  private EditText phoneNumber;
  private AuthLoginHelp mAuthLoginHelp;

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
    phoneNumber = bindView(R.id.phone_number);
  }

  private void initListener() {
    bindView(R.id.next_action, this);
    bindView(R.id.we_chat_login, this);
    mAuthLoginHelp = new AuthLoginHelp(getActivity());
    mAuthLoginHelp.callback(this);
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
        mPresenter.sendVerCode(phoneNumber.getText().toString().trim());
        break;
      case R.id.we_chat_login:
        boolean installWeChat =
            UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN);
        if (installWeChat) {
          mAuthLoginHelp.login(SHARE_MEDIA.WEIXIN);
        } else {
          ToastUtils.show("请先安装微信");
        }
        break;
      default:
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK
        && requestCode == VerificationCodeActivity.INPUT_VER_CODE_REQUEST) {
      mPresenter.login();
    }
  }

  @Override public void onStart(SHARE_MEDIA media) {
    //NetLoadingDialog.showLoading(getActivity(), false);
  }

  @Override public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
    Log.e("======>",map.toString());
    //NetLoadingDialog.dismissLoading();
    String uuid = map.get("uid");
  }

  @Override public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
    //NetLoadingDialog.dismissLoading();
  }

  @Override public void onCancel(SHARE_MEDIA media, int i) {
    //NetLoadingDialog.dismissLoading();
  }
}
