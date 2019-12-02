package com.nhbs.fenxiao.module.home.activity.presenter;

import com.nhbs.fenxiao.module.home.bean.HomeFindActivtyListBean;
import com.yu.common.mvp.Viewer;


public interface HomeEventViewer extends Viewer {
    void getFindActivtyListSuccess(HomeFindActivtyListBean homeFindActivtyListBean);

    void insertActivtySuccess();
}
