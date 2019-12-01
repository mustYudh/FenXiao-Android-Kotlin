package com.nhbs.fenxiao.module.store.activity.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.GetGoodsParams
import com.nhbs.fenxiao.module.store.bean.GetGoodsParamsNew
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

@SuppressLint("CheckResult")
class TypeInfoPresenter(viewer: TypeInfoViewer) : BaseViewPresenter<TypeInfoViewer>(viewer) {



  fun getGoodsList(params: GetGoodsParams) {
    XHttp.custom(AppApiServices::class.java)
        .getGoodsList(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<ApiResult<GoodsListBean>>())
        .subscribeWith(object : LoadingRequestSubscriber<ApiResult<GoodsListBean>>(activity!!,
            false) {
          override fun onSuccess(result: ApiResult<GoodsListBean>?) {
            val data = result?.data?.rows
            getViewer()?.setGoodsInfoList(data)

          }
        })
  }

    fun getGoodsListNew(params: GetGoodsParamsNew) {
        XHttp.custom(AppApiServices::class.java)
                .getGoodsListNew(HttpUtils.getJsonRequestBody(params))
                .compose(RxSchedulerUtils._io_main_o<ApiResult<GoodsListBean>>())
                .subscribeWith(object : LoadingRequestSubscriber<ApiResult<GoodsListBean>>(activity!!,
                        false) {
                    override fun onSuccess(result: ApiResult<GoodsListBean>?) {
                        val data = result?.data?.rows
                        getViewer()?.setGoodsInfoList(data)
                    }
                })
    }

  fun deleteTypeGoods(id:String,classId:String) {
    XHttpProxy.proxy(AppApiServices::class.java)
            .typeDeleteGoods(id, classId)
            .subscribeWith(object : TipRequestSubscriber<Any>() {
              override fun onSuccess(t: Any?) {
                showToast("删除成功")
                getViewer()?.deleteGoodsSuc()
              }
            })
  }
}