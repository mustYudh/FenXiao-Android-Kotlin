package com.nhbs.fenxiao.module.product.fragment.presenter;

import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.yu.common.mvp.Viewer;


public interface ProductFragmentViewer extends Viewer {
    void getMerchandiseClassSuccess(MerchandiseClassBean merchandiseClassBean);
}
