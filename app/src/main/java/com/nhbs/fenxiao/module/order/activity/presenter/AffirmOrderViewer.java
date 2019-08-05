package com.nhbs.fenxiao.module.order.activity.presenter;

import com.nhbs.fenxiao.module.order.bean.CreateUserOrderBean;
import com.nhbs.fenxiao.module.order.bean.FirstAddressBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.yu.common.mvp.Viewer;


public interface AffirmOrderViewer extends Viewer {
    void createUserOrderSuccess(CreateUserOrderBean createUserOrderBean);

    void getFirstAddress(FirstAddressBean firstAddressBean);

    void userToPaySuccess(PayInfo payInfo);
}
