package com.nhbs.fenxiao.module.order.activity.presenter;

import com.nhbs.fenxiao.module.order.bean.OrderDetailsBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean;
import com.yu.common.mvp.Viewer;


public interface OrderDetailsViewer extends Viewer {
    void getOrderInfoDetailSuccess(OrderDetailsBean orderDetailsBean);

    void confirmGoodsSuccess();

    void cancelOrderSuccess();

    void userToPaySuccess(PayInfo payInfo);

    void findExpSuccess(ExpInfoBean expInfoBean);
}
