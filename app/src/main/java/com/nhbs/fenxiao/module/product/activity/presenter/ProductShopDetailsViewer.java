package com.nhbs.fenxiao.module.product.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.FindMyShopMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.product.bean.ShopOtherUserDetailBean;
import com.nhbs.fenxiao.module.store.bean.UserShopShareBean;
import com.yu.common.mvp.Viewer;


public interface ProductShopDetailsViewer extends Viewer {
    void findMyShopMerchandiseListSuccess(FindMyShopMerchandiseListBean findMyShopMerchandiseListBean);

    void getShopOtherUserDetailSuccess(ShopOtherUserDetailBean shopOtherUserDetailBean);

    void userShareShopSuccess(UserShopShareBean userShopShareBean);

    void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean);

    void agentMerchandiseSuccess(FindMyShopMerchandiseListBean.ListBean item);
}
