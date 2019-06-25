package com.nhbs.fenxiao.module.login.presenter;

import android.text.TextUtils;
import com.yu.common.framework.BaseViewPresenter;


public class VerificationCodePresenter extends BaseViewPresenter<VerificationCodeViewer> {

    public VerificationCodePresenter(VerificationCodeViewer viewer) {
        super(viewer);
    }

    public void sendVerCode(String code) {
        if (!TextUtils.isEmpty(code)) {
            getViewer().loginSuccess();
        }
    }
}