package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.GetGoodsParams
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.exception.ApiException
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

/**
 * @author yudneghao
 * @date 2019-06-30
 */
@SuppressLint("CheckResult")
class MiniStoreGoodsInfoPresenter(viewer: MiniStoreGoodsInfoViewer) :
    BaseViewPresenter<MiniStoreGoodsInfoViewer>(viewer) {


  fun getGoodsList(params: GetGoodsParams, refreshLayout: RefreshLayout?, type: Int) {
    XHttp.custom(AppApiServices::class.java)
        .getGoodsList(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<GoodsListBean>())
        .subscribeWith(object : LoadingRequestSubscriber<GoodsListBean>(activity, false) {
          override fun onSuccess(result: GoodsListBean?) {
            val data = result?.list
            getViewer()?.setGoodsInfoList(data)
            if (refreshLayout != null) {
              if (type == 0) {
                refreshLayout.finishRefresh()
                if (data == null || data.size == 0) {
                  refreshLayout.finishRefreshWithNoMoreData()
                }
              } else {
                refreshLayout.finishLoadMore()
                refreshLayout.finishLoadMoreWithNoMoreData()
              }
            }
          }

          override fun onError(apiException: ApiException?) {
            super.onError(apiException)
            if (refreshLayout != null) {
              if (type == 0) {
                refreshLayout.finishRefresh()
                refreshLayout.finishRefreshWithNoMoreData()
              } else {
                refreshLayout.finishLoadMore()
              }
            }
          }
        })
  }

}