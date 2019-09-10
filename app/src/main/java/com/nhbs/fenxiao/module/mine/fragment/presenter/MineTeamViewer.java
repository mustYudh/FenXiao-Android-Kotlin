package com.nhbs.fenxiao.module.mine.fragment.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineGroupBean;
import com.yu.common.mvp.Viewer;


public interface MineTeamViewer extends Viewer {
    void mineGroupSuccess(MineGroupBean mineGroupBean);

}
