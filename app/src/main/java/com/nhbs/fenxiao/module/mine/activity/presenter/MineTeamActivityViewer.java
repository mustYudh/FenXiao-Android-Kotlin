package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineGroupBean;
import com.yu.common.mvp.Viewer;


public interface MineTeamActivityViewer extends Viewer {
    void mineGroupSuccess(MineGroupBean mineGroupBean);
}
