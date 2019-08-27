package com.nhbs.fenxiao.module.product.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class ProductClassifyFragmentPresenter extends BaseViewPresenter<ProductClassifyFragmentViewer> {

    public ProductClassifyFragmentPresenter(ProductClassifyFragmentViewer viewer) {
        super(viewer);
    }

    public void getMerchandiseClassList(String classId, int pageNum, int pageSize) {
        XHttpProxy.proxy(OtherApiServices.class)
                .findMerchandiseList(classId, pageNum, pageSize)
                .subscribeWith(new LoadingRequestSubscriber<FindMerchandiseListBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(FindMerchandiseListBean findMerchandiseListBean) {
                        assert getViewer() != null;
                        getViewer().getMerchandiseClassListSuccess(findMerchandiseListBean);
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

    public void agentMerchandise(FindMerchandiseListBean.RowsBean item) {
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