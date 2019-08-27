package com.nhbs.fenxiao.module.order.activity.presenter;

import com.nhbs.fenxiao.module.order.bean.OrderDetailsBean;
import com.yu.common.mvp.Viewer;


public interface OrderDetailsViewer extends Viewer {
    void getOrderInfoDetailSuccess(OrderDetailsBean orderDetailsBean);
}
