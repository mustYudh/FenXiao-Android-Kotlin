package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.ShopInfoBean
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.exception.ApiException
import com.yu.common.framework.BaseViewPresenter

/**
 * @author yudneghao
 * @date 2019-06-30
 */
@SuppressLint("CheckResult")
class MiniStorePresenter(viewer: MiniStoreViewer) : BaseViewPresenter<MiniStoreViewer>(viewer) {


  fun getShopInfo() {
    XHttpProxy.proxy(AppApiServices::class.java)
        .getShopInfo()
        .subscribeWith(object : TipRequestSubscriber<ShopInfoBean>() {
          override fun onSuccess(t: ShopInfoBean?) {
            getViewer()?.setShopInfo(t)
          }

          override fun onError(apiException: ApiException?) {
            if (apiException?.code == 10001) {
              getViewer()?.needOpenStore()
            } else {
              super.onError(apiException)
            }
          }
        })
  }


}