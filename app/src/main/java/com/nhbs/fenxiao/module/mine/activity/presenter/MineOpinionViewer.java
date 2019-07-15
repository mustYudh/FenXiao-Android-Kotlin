package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.yu.common.mvp.Viewer;


public interface MineOpinionViewer extends Viewer {
    void opinionAddSuccess();

    void uploadImgSuccess(UploadImgBean uploadImgBean);

    void uploadImgFail();

    void opinionAddFail();
}
