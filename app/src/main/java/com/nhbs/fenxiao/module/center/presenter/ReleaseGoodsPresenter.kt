package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.ReleaseGoodsParams
import com.xuexiang.xhttp2.XHttpProxy
import com.yu.common.framework.BaseViewPresenter


@SuppressLint("CheckResult")
class ReleaseGoodsPresenter(viewer: ReleaseGoodsViewer) : BaseViewPresenter<ReleaseGoodsViewer>(
    viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseGoodsImage(url)

  }


  fun releaseGoods(params : ReleaseGoodsParams) {
    XHttpProxy.proxy(AppApiServices::class.java)
        .releaseGoods(ReleaseGoodsParams())
        .subscribeWith(object : TipRequestSubscriber<Any>() {
          override fun onSuccess(t: Any?) {

          }
        })
  }
}