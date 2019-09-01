package com.nhbs.fenxiao.module.home.activity.presenter;

import com.nhbs.fenxiao.module.home.bean.AwardDetailsBean;
import com.yu.common.mvp.Viewer;


public interface AwardDetailsViewer extends Viewer {
    void activityShareDetailSuccess(AwardDetailsBean awardDetailsBean);
}
