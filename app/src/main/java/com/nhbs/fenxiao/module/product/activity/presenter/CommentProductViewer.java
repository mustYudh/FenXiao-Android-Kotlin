package com.nhbs.fenxiao.module.product.activity.presenter;

import com.nhbs.fenxiao.module.product.bean.ProductCommentBean;
import com.yu.common.mvp.Viewer;


public interface CommentProductViewer extends Viewer {
    void productCommentSuccess(ProductCommentBean productCommentBean);
}
