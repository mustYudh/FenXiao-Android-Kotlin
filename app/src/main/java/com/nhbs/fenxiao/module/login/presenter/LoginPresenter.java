package com.nhbs.fenxiao.module.login.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudenghao
 */
public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

    public LoginPresenter(LoginViewer viewer) {
        super(viewer);
    }

    public void afterLoginSuccess() {
        if (getViewer() == null) {
            return;
        }
        Bundle loginExtraBundle = getViewer().getLoginExtraBundle();
        String redirectActivityClassName = getViewer().getRedirectActivityClassName();
        String redirectOtherAction = getViewer().getRedirectOtherAction();
        if (loginExtraBundle == null) {

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

            }
        }
    }
}