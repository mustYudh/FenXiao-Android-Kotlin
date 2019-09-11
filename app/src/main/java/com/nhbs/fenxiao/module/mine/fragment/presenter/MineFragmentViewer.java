package com.nhbs.fenxiao.module.mine.fragment.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean;
import com.yu.common.mvp.Viewer;


public interface MineFragmentViewer extends Viewer {
    void getUserInfoSuccess(MineUserInfoBean mineUserInfoBean);

    void boundWinXinSuccess(MineUserInfoBean mineUserInfoBean);
}
