package com.nhbs.fenxiao.module.login.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.nhbs.fenxiao.action.BaseActionHelper;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.params.PostParams;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.home.HomePageActivity;
import com.nhbs.fenxiao.module.login.BindPhoneActivity;
import com.nhbs.fenxiao.module.login.LoginActivity;
import com.nhbs.fenxiao.module.login.VerificationCodeActivity;
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean;
import com.nhbs.fenxiao.module.login.bean.WeChatRegisterParams;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.model.ApiResult;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.PhoneUtils;
import com.yu.common.utils.RxSchedulerUtils;

/**
 * @author yudenghao
 */
@SuppressLint("CheckResult") public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

  public LoginPresenter(LoginViewer viewer) {
    super(viewer);
  }

  public void sendVerCode(String phone) {
    if (TextUtils.isEmpty(phone)) {
      ToastUtils.show("手机号输入不能为空");
      return;
    }
    if (!PhoneUtils.isPhoneLegal(phone)) {
      ToastUtils.show("请输入正确的手机号码");
      return;
    }
    XHttpProxy.proxy(AppApiServices.class)
        .sendVerCode(phone)
        .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
          @Override protected void onSuccess(Object o) {
            getLaunchHelper().startActivityForResult(
                VerificationCodeActivity.getIntent(getActivity(), phone),
                VerificationCodeActivity.INPUT_VER_CODE_REQUEST);
          }
        });
  }

  public void loginWeChat(String openId,String name,String iconUrl) {
    XHttp.custom(AppApiServices.class)
        .weChatLogin(PostParams.createParams().put("openId", openId).creatBody())
        .compose(RxSchedulerUtils.<ApiResult<LoginInfoBean>>_io_main_o())
        .subscribeWith(
            new LoadingRequestSubscriber<ApiResult<LoginInfoBean>>(getActivity(), false) {
              @Override protected void onSuccess(ApiResult<LoginInfoBean> bean) {
                if (bean.getCode() == 7000) {
                  WeChatRegisterParams params = new WeChatRegisterParams(openId,name,iconUrl,"","","");
                  getLauncherHelper().startActivityForResult(BindPhoneActivity.Companion.getIntent(getActivity(),params),
                      LoginActivity.BIND_PHONE_SUCCESS_REQUEST_CODE);
                } else if (bean.getCode() == 2000) {
                  UserProfile.getInstance().appLogin(bean.getData());
                  getLauncherHelper().startActivity(HomePageActivity.class);
                  getActivity().finish();
                }
              }
            });
  }

  public void login() {
    afterLoginSuccess();
  }

  private void afterLoginSuccess() {
    if (getViewer() == null) {
      return;
    }
    Bundle loginExtraBundle = getViewer().getLoginExtraBundle();
    String redirectActivityClassName = getViewer().getRedirectActivityClassName();
    String redirectOtherAction = getViewer().getRedirectOtherAction();
    if (loginExtraBundle == null) {
      LauncherHelper.from(getActivity()).startActivity(HomePageActivity.class);
      getActivity().setResult(Activity.RESULT_OK);
      getActivity().finish();
      return;
    }
    if (!TextUtils.isEmpty(redirectActivityClassName)) {
      Intent intent = new Intent();
      intent.setComponent(new ComponentName(getActivity(), redirectActivityClassName));
      intent.putExtras(loginExtraBundle);
      getLauncherHelper().startActivity(intent);
      getActivity().finish();
      return;
    }
    if (!TextUtils.isEmpty(redirectOtherAction)) {
      switch (redirectOtherAction) {
        case BaseActionHelper.LINK_URL:
          BaseActionHelper.with(getActivity())
              .handleAction(loginExtraBundle.getString(BaseActionHelper.LINK_URL), false);
          getActivity().finish();
          break;
        default:
          getActivity().finish();
          break;
      }
    }
  }
}