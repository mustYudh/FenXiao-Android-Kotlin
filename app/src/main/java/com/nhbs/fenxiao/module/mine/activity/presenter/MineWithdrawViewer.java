package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean;
import com.yu.common.mvp.Viewer;


public interface MineWithdrawViewer extends Viewer {
    void createUserWithdrawSuccess();

    void getUserInfoSuccess(MineUserInfoBean mineUserInfoBean);
}
