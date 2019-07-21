package com.nhbs.fenxiao.module.home.fragment.presenter;

import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.yu.common.mvp.Viewer;


public interface HomeFragmentViewer extends Viewer {
    void getMerchandiseClassListSuccess(FindMerchandiseListBean findMerchandiseListBean);
}
