package com.nhbs.fenxiao.data;

import android.content.Context;
import com.nhbs.fenxiao.APP;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private static UserProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";

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


    /**
     * 退出登录
     */
    public void clean() {
        spHelper.clear();
    }

}
