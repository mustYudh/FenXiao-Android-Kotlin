package com.nhbs.fenxiao.module.product.activity.presenter;

import android.annotation.SuppressLint;

import com.nhbs.fenxiao.http.api.OtherApiServices;
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber;
import com.nhbs.fenxiao.module.product.bean.ProductCommentBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class CommentProductPresenter extends BaseViewPresenter<CommentProductViewer> {

    public CommentProductPresenter(CommentProductViewer viewer) {
        super(viewer);
    }

    public void productComment(String targetId, String content) {
        XHttpProxy.proxy(OtherApiServices.class)
                .productComment(targetId, content)
                .subscribeWith(new LoadingRequestSubscriber<ProductCommentBean>(getActivity(), false) {
                    @Override
                    protected void onSuccess(ProductCommentBean productCommentBean) {
                        assert getViewer() != null;
                        getViewer().productCommentSuccess(productCommentBean);
                    }
                });
    }
}