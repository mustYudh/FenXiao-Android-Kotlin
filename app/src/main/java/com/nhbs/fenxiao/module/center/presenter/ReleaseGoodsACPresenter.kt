package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.ReleaseActivityParams
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils


@SuppressLint("CheckResult")
class ReleaseGoodsACPresenter(
    viewer: ReleaseGoodsACActivityViewer) : BaseViewPresenter<ReleaseGoodsACActivityViewer>(
    viewer) {


  fun releaseActivity(params: ReleaseActivityParams) {
    if (params.checkParams()) {
      XHttp.custom(AppApiServices::class.java)
          .releaseActivity(HttpUtils.getJsonRequestBody(params))
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
}