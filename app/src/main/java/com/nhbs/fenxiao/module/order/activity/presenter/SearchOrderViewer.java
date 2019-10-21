package com.nhbs.fenxiao.module.order.activity.presenter;

import com.nhbs.fenxiao.module.order.bean.SearchOrderBean;
import com.yu.common.mvp.Viewer;


public interface SearchOrderViewer extends Viewer {
    void getSearchOrderSuccess(SearchOrderBean searchOrderBean);
}
