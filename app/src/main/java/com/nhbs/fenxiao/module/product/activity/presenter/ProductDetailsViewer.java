package com.nhbs.fenxiao.module.product.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.CommentListBean;
import com.nhbs.fenxiao.module.product.bean.FindMyShopMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.yu.common.mvp.Viewer;


public interface ProductDetailsViewer extends Viewer {
    void getMerchandiseDetailSuccess(MerchandiseDetailBean merchandiseDetailBean);

    void agentMerchandiseSuccess(MerchandiseDetailBean merchandiseDetailBean);

    void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean);

    void likeProductSuccess(MerchandiseDetailBean merchandiseDetailBean);

    void commentListSuccess(CommentListBean commentListBean);

    void findMyShopMerchandiseListSuccess(FindMyShopMerchandiseListBean findMyShopMerchandiseListBean);
}
