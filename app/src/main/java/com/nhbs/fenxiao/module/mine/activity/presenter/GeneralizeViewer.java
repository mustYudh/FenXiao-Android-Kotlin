package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.yu.common.mvp.Viewer;


public interface GeneralizeViewer extends Viewer {
    void querySpreadLogsListSuccess(MineSpreadLogsListBean spreadLogsListBean);
}
