package com.nhbs.fenxiao.module.home.fragment.presenter;

import com.nhbs.fenxiao.module.home.bean.HomeFindAdvertisingListBean;
import com.yu.common.mvp.Viewer;


public interface RewardAdvertisingFragmentViewer extends Viewer {
    void getFindAdvertisingListSuccess(HomeFindAdvertisingListBean homeFindAdvertisingListBean);
}
