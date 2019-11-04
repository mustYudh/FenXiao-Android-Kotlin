package com.nhbs.fenxiao.module.login.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
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
                    .subscribeWith(new LoadingRequestSubscriber<LoginInfoBean>(getActivity(),false) {
                        @Override
                        protected void onSuccess(LoginInfoBean loginInfoBean) {
                            assert getViewer() != null;
                            UserProfile.getInstance().appLogin(loginInfoBean);
                            getViewer().loginSuccess();
                        }

                      @Override protected void onError(ApiException apiException) {
                        super.onError(apiException);
                        assert getViewer() != null;
                        if (apiException.getCode() == 3003) {
                            getActivity().finish();
                        }
                        getViewer().loginFailed();
                      }
                    });



        }
    }
}