package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import com.yu.common.framework.BaseViewPresenter

@SuppressLint("CheckResult")
class ReleaseAdvertisingPresenter(
    viewer: ReleaseAdvertisingViewer) : BaseViewPresenter<ReleaseAdvertisingViewer>(viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseAdvertisingImage(url)
  }


  /**
   *  XHttp.post("/upload")
  .baseUrl("http://139.180.218.55:8060")
  .subUrl("/api")
  .uploadFile("file", File(url[0])) { _, _, _ -> }
  .execute(Any::class.java)
  .compose(RxLifecycle.with(activity).bindToLifecycle<Any>()).subscribeWith(
  object : TipRequestSubscriber<Any>() {
  override fun onSuccess(t: Any?) {

  }

  override fun onError(e: ApiException) {
  NetLoadingDialog.dismissLoading()
  super.onError(e)
  }

  override fun onComplete() {
  NetLoadingDialog.dismissLoading()
  super.onComplete()
  }
  }
  )
   */
}
