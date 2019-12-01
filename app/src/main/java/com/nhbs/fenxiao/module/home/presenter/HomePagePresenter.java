package com.nhbs.fenxiao.module.home.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.NimeInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudenghao
 */
@SuppressLint("CheckResult")
public class HomePagePresenter extends BaseViewPresenter<HomePageViewer> {

    public HomePagePresenter(HomePageViewer viewer) {
        super(viewer);
    }

    public void loginNime() {

        XHttpProxy.proxy(AppApiServices.class).getNimeInfo().subscribeWith(new TipRequestSubscriber<NimeInfoBean>() {
            @Override
            protected void onSuccess(NimeInfoBean infoBean) {
                UserProfile.getInstance().setNimeToken(infoBean.neteaseCommunicationToken);
                UserProfile.getInstance().setNimeAccount(infoBean.neteaseCommunicationAccid);
                LoginInfo loginInfo = new LoginInfo(UserProfile.getInstance().getNimeAccount(), UserProfile.getInstance().getNimeToken());
                NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        Log.e("----------------->","登录成功");
                        NimUIKit.loginSuccess(loginInfo.getAccount());
                        NIMClient.toggleNotification(true);
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.e("----------------->","登录失败" + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.e("----------------->","登录异常");
                    }
                });
            }
        });


    }
}