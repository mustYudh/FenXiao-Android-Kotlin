package com.nhbs.fenxiao.module.mine.fragment.presenter;

import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.yu.common.mvp.Viewer;


public interface MineOrderListFragmentViewer extends Viewer {
    void getMineOrderSuccess(MineOrderListBean mineOrderListBean);

    void confirmGoodsSuccess();
}
