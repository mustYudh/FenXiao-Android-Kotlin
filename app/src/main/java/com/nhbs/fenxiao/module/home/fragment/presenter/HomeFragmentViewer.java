package com.nhbs.fenxiao.module.home.fragment.presenter;

import com.nhbs.fenxiao.module.home.bean.HomeBannerBean;
import com.nhbs.fenxiao.module.home.bean.HomeHotAdvertiseBean;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.yu.common.mvp.Viewer;


public interface HomeFragmentViewer extends Viewer {
    void getMerchandiseClassListSuccess(FindMerchandiseListBean findMerchandiseListBean);

    void getBannerListSuccess(HomeBannerBean homeBannerBean);

    void getHotAdvertiseListSuccess(HomeHotAdvertiseBean homeHotAdvertiseBean);

    void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean);

    void agentMerchandiseSuccess(FindMerchandiseListBean.RowsBean item);

    void getBannerListFail();
}
