package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.UserAccountInfo;
import com.yu.common.mvp.Viewer;


public interface MineSettingsViewer extends Viewer {
    void getUserAccountInfoSuccess(UserAccountInfo accountInfo);
}
