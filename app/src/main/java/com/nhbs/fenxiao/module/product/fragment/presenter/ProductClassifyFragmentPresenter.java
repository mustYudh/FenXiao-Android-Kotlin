package com.nhbs.fenxiao.module.product.fragment.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
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
}