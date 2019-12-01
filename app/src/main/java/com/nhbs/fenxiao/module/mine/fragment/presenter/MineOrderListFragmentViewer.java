package com.nhbs.fenxiao.module.mine.fragment.presenter;

import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean;
import com.yu.common.mvp.Viewer;


public interface MineOrderListFragmentViewer extends Viewer {
    void getMineOrderSuccess(MineOrderListBean mineOrderListBean);

    void confirmGoodsSuccess();

    void cancelOrderSuccess();

    void userToPaySuccess(PayInfo payInfo);

    void findExpSuccess(ExpInfoBean expInfoBean);
}
