package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.GoodsTypeBean
import com.xuexiang.xhttp2.XHttpProxy
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


  fun getAdType() {
    XHttpProxy.proxy(AppApiServices::class.java)
        .getAdType()
        .subscribeWith(object : LoadingRequestSubscriber<GoodsTypeBean>(activity!!,false) {
          override fun onSuccess(goods: GoodsTypeBean?) {
            goods?.rows?.forEach {
              it.classify = it.name
            }
            getViewer()?.setType(goods?.rows!!)
          }
        })
  }
}
