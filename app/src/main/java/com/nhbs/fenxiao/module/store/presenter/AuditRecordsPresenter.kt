package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.ActivityListInfo
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.exception.ApiException
import com.yu.common.framework.BaseViewPresenter

/**
 * @author yudneghao
 * @date 2019-08-17
 */
class AuditRecordsPresenter(viewer: AuditRecordsViewer) : BaseViewPresenter<AuditRecordsViewer>(viewer) {

  @SuppressLint("CheckResult")
  fun getRecordList(pageNum: Int? = 1, refreshLayout: RefreshLayout? = null, type: Int? = 0) {
    XHttpProxy.proxy(AppApiServices::class.java)
        .getMyActivityList(pageNum)
        .subscribeWith(object : TipRequestSubscriber<ActivityListInfo>() {
          override fun onSuccess(result: ActivityListInfo?) {
            val data = result?.rows
            getViewer()?.setRecordInfoList(data)
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