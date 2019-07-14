package com.nhbs.fenxiao.module.center.presenter

import com.yu.common.framework.BaseViewPresenter


class ReleaseGoodsPresenter(viewer: ReleaseGoodsViewer) : BaseViewPresenter<ReleaseGoodsViewer>(
    viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseGoodsImage(url)
  }
}