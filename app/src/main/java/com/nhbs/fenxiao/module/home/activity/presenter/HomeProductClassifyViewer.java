package com.nhbs.fenxiao.module.home.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.yu.common.mvp.Viewer;


public interface HomeProductClassifyViewer extends Viewer {
    void getMerchandiseClassSuccess(MerchandiseClassBean merchandiseClassBean);
}
