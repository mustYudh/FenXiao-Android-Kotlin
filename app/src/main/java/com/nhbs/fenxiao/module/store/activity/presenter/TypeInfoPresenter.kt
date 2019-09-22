package com.nhbs.fenxiao.module.store.activity.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.GetGoodsParams
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.exception.ApiException
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

@SuppressLint("CheckResult")
class TypeInfoPresenter(viewer: TypeInfoViewer) : BaseViewPresenter<TypeInfoViewer>(viewer) {



  fun getGoodsList(params: GetGoodsParams, refreshLayout: RefreshLayout?, type: Int? = 0) {
    XHttp.custom(AppApiServices::class.java)
        .getGoodsList(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<ApiResult<GoodsListBean>>())
        .subscribeWith(object : LoadingRequestSubscriber<ApiResult<GoodsListBean>>(activity!!,
            false) {
          override fun onSuccess(result: ApiResult<GoodsListBean>?) {
            val data = result?.data?.rows
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