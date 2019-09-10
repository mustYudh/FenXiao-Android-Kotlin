package com.nhbs.fenxiao.module.mine.fragment.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.yu.common.mvp.Viewer;


public interface TeamGeneralizeViewer extends Viewer {
    void querySpreadLogsListSuccess(MineSpreadLogsListBean spreadLogsListBean);
}
