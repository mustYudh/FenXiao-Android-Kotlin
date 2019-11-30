package com.nhbs.fenxiao.module.product.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.FindMyShopMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.product.bean.ShopOtherUserDetailBean;
import com.nhbs.fenxiao.module.store.bean.UserShopShareBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class ProductShopDetailsPresenter extends BaseViewPresenter<ProductShopDetailsViewer> {

    public ProductShopDetailsPresenter(ProductShopDetailsViewer viewer) {
        super(viewer);
    }

    public void likeProduct(String id, String type) {
        XHttpProxy.proxy(OtherApiServices.class)
                .likeProduct(id, type)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().likeProductSuccess();
                    }
                });
    }

    public void likeProductCancle(String id, String type) {
        XHttpProxy.proxy(OtherApiServices.class)
                .likeProductCancle(id, type)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().likeProductCancleSuccess();
                    }
                });
    }


    public void getMerchandiseClass() {
        XHttpProxy.proxy(OtherApiServices.class)
                .merchandiseClass()
                .subscribeWith(new LoadingRequestSubscriber<MerchandiseClassBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(MerchandiseClassBean merchandiseClassBean) {
                        assert getViewer() != null;
                        getViewer().getMerchandiseClassSuccess(merchandiseClassBean);
                    }
                });
    }

    public void findMyShopMerchandiseList(String classId, String pageNum, String pageSize, String shopId) {
        XHttpProxy.proxy(OtherApiServices.class)
                .findMyShopMerchandiseShopList(classId, pageNum, pageSize, shopId)
                .subscribeWith(new LoadingRequestSubscriber<FindMyShopMerchandiseListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(FindMyShopMerchandiseListBean findMyShopMerchandiseListBean) {
                        assert getViewer() != null;
                        getViewer().findMyShopMerchandiseListSuccess(findMyShopMerchandiseListBean);
                    }
                });
    }

    public void findMyShopMerchandiseListAll(String pageNum, String pageSize, String shopId) {
        XHttpProxy.proxy(OtherApiServices.class)
                .findMyShopMerchandiseList(pageNum, pageSize, shopId)
                .subscribeWith(new LoadingRequestSubscriber<FindMyShopMerchandiseListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(FindMyShopMerchandiseListBean findMyShopMerchandiseListBean) {
                        assert getViewer() != null;
                        getViewer().findMyShopMerchandiseListSuccess(findMyShopMerchandiseListBean);
                    }
                });
    }

    public void getShopOtherUserDetail(String shopId) {
        XHttpProxy.proxy(OtherApiServices.class)
                .shopOtherUserDetail(shopId)
                .subscribeWith(new TipRequestSubscriber<ShopOtherUserDetailBean>() {
                    @Override
                    protected void onSuccess(ShopOtherUserDetailBean shopOtherUserDetailBean) {
                        assert getViewer() != null;
                        getViewer().getShopOtherUserDetailSuccess(shopOtherUserDetailBean);
                    }
                });
    }

    public void userShareShop(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .userShareShop(id)
                .subscribeWith(new LoadingRequestSubscriber<UserShopShareBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(UserShopShareBean userShopShareBean) {
                        assert getViewer() != null;
                        getViewer().userShareShopSuccess(userShopShareBean);
                    }
                });
    }

    public void advertiseShare(String id) {
        XHttpProxy.proxy(OtherApiServices.class)
                .shareMerchandise(id)
                .subscribeWith(new LoadingRequestSubscriber<ShareMerchandiseBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(ShareMerchandiseBean shareMerchandiseBean) {
                        assert getViewer() != null;
                        getViewer().advertiseShareSuccess(shareMerchandiseBean);
                    }
                });
    }

    public void agentMerchandise(FindMyShopMerchandiseListBean.ListBean item) {
        XHttpProxy.proxy(OtherApiServices.class)
                .agentMerchandise(item.id)
                .subscribeWith(new LoadingRequestSubscriber<Object>(getActivity(), false) {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().agentMerchandiseSuccess(item);
                    }
                });
    }
}