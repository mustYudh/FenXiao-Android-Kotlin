package com.nhbs.fenxiao.module.order.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.yu.common.mvp.Viewer;


public interface PaySuccessViewer extends Viewer {
    void getMerchandiseClassListSuccess(FindMerchandiseListBean findMerchandiseListBean);

    void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean);

    void agentMerchandiseSuccess(FindMerchandiseListBean.RowsBean item);
}
