package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.GetWithdrawInfoBean;
import com.yu.common.mvp.Viewer;


public interface MineWithdrawInfoViewer extends Viewer {
    void getWithdrawByKeySuccess(GetWithdrawInfoBean getWithdrawInfoBean);
}
