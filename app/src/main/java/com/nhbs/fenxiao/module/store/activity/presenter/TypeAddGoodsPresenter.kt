package com.nhbs.fenxiao.module.store.activity.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.GetOtherGoodsParams
import com.nhbs.fenxiao.module.store.bean.OtherTypeGoodsBean
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

@SuppressLint("CheckResult")
class TypeAddGoodsPresenter(viewer: TypeAddGoodsViewer) : BaseViewPresenter<TypeAddGoodsViewer>(viewer) {

    fun getOtherGoodsList(params: GetOtherGoodsParams) {
        XHttp.custom(AppApiServices::class.java)
                .getOtherGoodsList(HttpUtils.getJsonRequestBody(params))
                .compose(RxSchedulerUtils._io_main_o<ApiResult<OtherTypeGoodsBean>>())
                .subscribeWith(object : LoadingRequestSubscriber<ApiResult<OtherTypeGoodsBean>>(activity!!,
                        false) {
                    override fun onSuccess(result: ApiResult<OtherTypeGoodsBean>?) {
                        val data = result?.data?.rows
                        getViewer()?.setOtherGoodsInfoList(data)
                    }
                })
    }

    fun addGoods(id:String,classId: String) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .typeAddGoods(id, classId)
                .subscribeWith(object : TipRequestSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        showToast("添加成功")
                        getViewer()?.addGoodsSuc()
                    }
                })
    }

}