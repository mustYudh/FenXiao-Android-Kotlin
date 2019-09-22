package com.nhbs.fenxiao.module.store.activity.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.OtherApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.exception.ApiException
import com.yu.common.framework.BaseViewPresenter

@SuppressLint("CheckResult")
class TypeInfoPresenter(viewer: TypeInfoViewer) : BaseViewPresenter<TypeInfoViewer>(viewer) {


  fun getMerchandiseClassList(classId: String, pageNum: Int = 1, refreshLayout: RefreshLayout? = null, type: Int? = 0) {
    XHttpProxy.proxy(OtherApiServices::class.java)
        .findMerchandiseList(classId, pageNum, 10)
        .subscribeWith(object : LoadingRequestSubscriber<FindMerchandiseListBean>(activity,
            false) {
          override fun onSuccess(result: FindMerchandiseListBean) {
            val data = result.rows
            getViewer()!!.getMerchandiseClassListSuccess(result.rows)
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