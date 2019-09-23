package com.nhbs.fenxiao.module.home.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.yu.common.mvp.Viewer;


public interface ProductSearchViewer extends Viewer {
    void searchMerchandiseListSuccess(FindMerchandiseListBean findMerchandiseListBean);

    void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean);

    void agentMerchandiseSuccess(FindMerchandiseListBean.RowsBean item);
}
