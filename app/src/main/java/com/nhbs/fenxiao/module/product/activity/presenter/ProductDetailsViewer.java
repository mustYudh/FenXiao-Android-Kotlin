package com.nhbs.fenxiao.module.product.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.yu.common.mvp.Viewer;


public interface ProductDetailsViewer extends Viewer {
    void getMerchandiseDetailSuccess(MerchandiseDetailBean merchandiseDetailBean);

    void agentMerchandiseSuccess(MerchandiseDetailBean merchandiseDetailBean);
}
