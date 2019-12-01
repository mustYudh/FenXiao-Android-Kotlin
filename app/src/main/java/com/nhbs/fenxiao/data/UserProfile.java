package com.nhbs.fenxiao.data;

import android.content.Context;
import android.text.TextUtils;

import com.nhbs.fenxiao.APP;
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private static UserProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";
    private static final String TOKEN = "token";
    private static final String ISMERCHANT = "isMerchant";
    private static final String ACCOUNT = "account";
    private static final String NIME_ACCOUNT = "nime_account";
    private static final String NIME_TOKEN = "nime_token";

    private SharedPreferencesHelper spHelper;

    private UserProfile() {
        spHelper = SharedPreferencesHelper.create(
                APP.getInstance().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE));
    }

    public synchronized static UserProfile getInstance() {
        if (instance == null) {
            synchronized (UserProfile.class) {
                if (instance == null) {
                    instance = new UserProfile();
                }
            }
        }
        return instance;
    }

    public void appLogin(LoginInfoBean loginInfo) {
        setToken(loginInfo.token);
        setIsMerchant(loginInfo.user.isMerchant);
    }

    public void setToken(String token) {
        spHelper.putString(TOKEN, token);
    }

    public void setAccount(String account) {
        spHelper.putString(ACCOUNT, account);
    }

    public void setNimeToken(String token) {
        spHelper.putString(NIME_TOKEN, token);
    }


    public String getNimeToken() {
        return spHelper.getString(NIME_TOKEN, "");
    }


    public String getNimeAccount() {
        return spHelper.getString(NIME_ACCOUNT, "");
    }


    public void setNimeAccount(String account) {
        spHelper.putString(NIME_ACCOUNT, account);
    }


    public String getAccount() {
        return spHelper.getString(ACCOUNT, "");
    }

    public String getAppToken() {
        return spHelper.getString(TOKEN, "");
    }

    public void setIsMerchant(int isMerchant) {
        spHelper.putInt(ISMERCHANT, isMerchant);
    }

    public int getIsMerchant() {
        return spHelper.getInt(ISMERCHANT, 0);
    }

    /**
     * 退出登录
     */
    public void clean() {
        spHelper.clear();
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getAppToken());
    }
}
