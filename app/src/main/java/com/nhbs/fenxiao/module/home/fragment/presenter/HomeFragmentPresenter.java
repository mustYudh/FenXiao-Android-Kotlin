package com.nhbs.fenxiao.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.module.home.bean.HomeBannerBean;
import com.nhbs.fenxiao.module.home.bean.HomeHotAdvertiseBean;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeFragmentPresenter extends BaseViewPresenter<HomeFragmentViewer> {

    public HomeFragmentPresenter(HomeFragmentViewer viewer) {
        super(viewer);
    }

    public void getMerchandiseClassList(String type, int pageNum, int pageSize) {
        XHttpProxy.proxy(OtherApiServices.class)
                .homeFindMerchandiseList(type, pageNum, pageSize)
                .subscribeWith(new LoadingRequestSubscriber<FindMerchandiseListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(FindMerchandiseListBean findMerchandiseListBean) {
                        assert getViewer() != null;
                        getViewer().getMerchandiseClassListSuccess(findMerchandiseListBean);
                    }
                });
    }

    public void getBannerList() {
        XHttpProxy.proxy(OtherApiServices.class)
                .bannerList()
                .subscribeWith(new TipRequestSubscriber<HomeBannerBean>() {
                    @Override
                    protected void onSuccess(HomeBannerBean homeBannerBean) {
                        assert getViewer() != null;
                        getViewer().getBannerListSuccess(homeBannerBean);
                    }
                });
    }

    public void getHotAdvertiseList(String province, String city, String district) {
        XHttpProxy.proxy(OtherApiServices.class)
                .getHotAdvertise(province, city, district)
                .subscribeWith(new TipRequestSubscriber<HomeHotAdvertiseBean>() {
                    @Override
                    protected void onSuccess(HomeHotAdvertiseBean homeHotAdvertiseBean) {
                        assert getViewer() != null;
                        getViewer().getHotAdvertiseListSuccess(homeHotAdvertiseBean);
                    }
                });
    }
}