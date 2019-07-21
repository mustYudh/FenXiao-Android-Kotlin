package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import com.yu.common.framework.BaseViewPresenter

@SuppressLint("CheckResult")
class ReleaseAdvertisingPresenter(
    viewer: ReleaseAdvertisingViewer) : BaseViewPresenter<ReleaseAdvertisingViewer>(viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseAdvertisingImage(url)

//    UploadUtils.uploadFile(activity, url, "124","png") { fileList ->
//      for (result in fileList) {
//        Log.e("======>result", result)
//      }
//    }
  }
}
