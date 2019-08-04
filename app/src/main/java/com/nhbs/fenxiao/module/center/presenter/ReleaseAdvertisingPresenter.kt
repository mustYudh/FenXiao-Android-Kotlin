package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.GoodsTypeBean
import com.nhbs.fenxiao.module.center.bean.ReleaseAdParams
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.oss.UploadUtils
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

@SuppressLint("CheckResult")
class ReleaseAdvertisingPresenter(
    viewer: ReleaseAdvertisingViewer) : BaseViewPresenter<ReleaseAdvertisingViewer>(viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseAdvertisingImage(url)
  }


  fun getAdType() {
    XHttpProxy.proxy(AppApiServices::class.java)
        .getAdType()
        .subscribeWith(object : LoadingRequestSubscriber<GoodsTypeBean>(activity!!, false) {
          override fun onSuccess(goods: GoodsTypeBean?) {
            goods?.rows?.forEach {
              it.classify = it.name
            }
            getViewer()?.setType(goods?.rows!!)
          }
        })
  }


  fun releaseAD(params: ReleaseAdParams, url: ArrayList<String>) {
    if (params.checkParams()) {
      UploadUtils.uploadFile(activity, url, "Fa_Bu_AD", "png") { fileList ->
        params.imgs = ""
        fileList.forEachIndexed { index, result ->
          params.imgs += "$result${if (index < fileList.size - 2) "," else ""}"
        }
        commit(params)
      }
    }
  }

  private fun commit(params: ReleaseAdParams) {
    XHttp.custom(AppApiServices::class.java)
        .releaseAD(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<ApiResult<Any>>())
        .subscribeWith(object : LoadingRequestSubscriber<ApiResult<Any>>(activity, false) {
          override fun onSuccess(t: ApiResult<Any>?) {
            if (t?.code == 2000) {
              showToast("发布成功")
              activity?.finish()
            } else if (!t?.msg.checkTextEmpty()) {
              showToast(t?.msg!!)
            }
          }
        })
  }


}
