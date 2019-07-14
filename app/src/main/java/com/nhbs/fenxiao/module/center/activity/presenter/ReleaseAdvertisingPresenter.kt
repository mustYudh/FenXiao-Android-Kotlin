package com.nhbs.fenxiao.module.center.activity.presenter

import com.yu.common.framework.BaseViewPresenter

class ReleaseAdvertisingPresenter(
    viewer: ReleaseAdvertisingViewer) : BaseViewPresenter<ReleaseAdvertisingViewer>(viewer) {

  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseAdvertisingImage(url)
  }
}
