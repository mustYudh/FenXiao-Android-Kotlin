package com.nhbs.fenxiao.module.login.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.nhbs.fenxiao.action.BaseActionHelper;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.home.HomePageActivity;
import com.nhbs.fenxiao.module.login.VerificationCodeActivity;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.PhoneUtils;

/**
 * @author yudenghao
 */
@SuppressLint("CheckResult")
public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

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
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(),false) {
                    @Override
                    protected void onSuccess(Object o) {
                        getLaunchHelper().startActivityForResult(VerificationCodeActivity.getIntent(getActivity(), phone), VerificationCodeActivity.INPUT_VER_CODE_REQUEST);
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
                    BaseActionHelper.with(getActivity()).handleAction(loginExtraBundle.getString(BaseActionHelper.LINK_URL), false);
                    getActivity().finish();
                    break;
                default:
                    getActivity().finish();
                    break;
            }
        }
    }
}