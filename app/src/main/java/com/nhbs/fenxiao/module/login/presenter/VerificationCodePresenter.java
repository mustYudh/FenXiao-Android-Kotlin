package com.nhbs.fenxiao.module.login.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.NoTipRequestSubscriber;
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;


/**
 * @author yudneghao
 */
@SuppressLint("CheckResult")
public class VerificationCodePresenter extends BaseViewPresenter<VerificationCodeViewer> {

    public VerificationCodePresenter(VerificationCodeViewer viewer) {
        super(viewer);
    }


    public void sendVerCode(String phone, String code) {
        if (!TextUtils.isEmpty(code)) {
            XHttpProxy.proxy(AppApiServices.class).login(phone, code, "")
                    .subscribeWith(new NoTipRequestSubscriber<LoginInfoBean>() {
                        @Override
                        protected void onSuccess(LoginInfoBean loginInfoBean) {
                            assert getViewer() != null;
                            UserProfile.getInstance().appLogin(loginInfoBean);
                            getViewer().loginSuccess();
                        }
                    });

        }
    }
}