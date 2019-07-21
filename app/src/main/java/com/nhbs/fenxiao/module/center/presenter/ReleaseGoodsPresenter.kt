package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.ReleaseGoodsParams
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils


@SuppressLint("CheckResult")
class ReleaseGoodsPresenter(viewer: ReleaseGoodsViewer) : BaseViewPresenter<ReleaseGoodsViewer>(
    viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseGoodsImage(url)

  }


  fun releaseGoods(params : ReleaseGoodsParams) {
    Log.e("======>",params.toString())
//
    XHttp.custom(AppApiServices::class.java)
        .releaseGoods(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<ApiResult<Any>>())
        .subscribeWith(object: TipRequestSubscriber<ApiResult<Any>>() {
          override fun onSuccess(t: ApiResult<Any>?) {

          }

        })
  }
}