package com.nhbs.fenxiao.module.store.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.yu.common.mvp.Viewer;


public interface RedactStoreViewer extends Viewer {
    void uploadImgSuccess(UploadImgBean uploadImgBean);

    void uploadImgFail();

    void userShopUpdateSuccess();

    void userShopUpdateFail();
}
