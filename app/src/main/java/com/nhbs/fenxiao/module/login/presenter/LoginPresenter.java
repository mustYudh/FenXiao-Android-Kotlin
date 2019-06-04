package com.nhbs.fenxiao.module.login.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.nhbs.fenxiao.action.BaseActionHelper;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.module.home.HomePageActivity;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.launche.LauncherHelper;

/**
 * @author yudenghao
 */
public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

    public LoginPresenter(LoginViewer viewer) {
        super(viewer);
    }

    public void login() {
        UserProfile.getInstance().setToken("1111");
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