package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.QueryShopKeeperOrdersParams
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

/**
 * @author yudneghao
 * @date 2019-09-14
 */
@SuppressLint("CheckResult")
class OrderManagerPresenter(viewer: OrderManagerViewer) : BaseViewPresenter<OrderManagerViewer>(
    viewer) {


  fun findMyShopMerchandiseList(params: QueryShopKeeperOrdersParams) {
    XHttp.custom(AppApiServices::class.java)
        .queryShopKeeperOrders(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<ApiResult<Any>>())
        .subscribeWith(object : TipRequestSubscriber<Any>() {
          override fun onSuccess(t: Any?) {

          }

        })
  }

}