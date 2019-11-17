package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.*
import com.nhbs.fenxiao.module.store.pop.ChooseTypePopUpWindow
import com.nhbs.fenxiao.utils.showToast
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils

@SuppressLint("CheckResult")
class NotSetTypePresenter(viewer: NotSetTypeViewer) : BaseViewPresenter<NotSetTypeViewer>(viewer) {


    fun getGoodsList(params: GetGoodsParams, refreshLayout: RefreshLayout?, type: Int? = 0) {
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


    fun getTypeGoodsCount() {
        XHttpProxy.proxy(AppApiServices::class.java)
                .getGoodsTypeCount()
                .subscribeWith(object : TipRequestSubscriber<TypeCountListBean>() {
                    override fun onSuccess(data: TypeCountListBean?) {
                        val list = ArrayList<ClassTOS>()
                        if (data != null) {
                            for (item in data.classListTOS) {
                                if (item.classify != "未分类") {
                                    list.add(item)
                                }
                            }
                        }

                        getViewer()?.showChooseTypeDialog(list)
                    }

                })
    }

    fun addGoods(id:String,classId: String,chooseTypePopUpWindow: ChooseTypePopUpWindow?) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .typeAddGoods(id, classId)
                .subscribeWith(object : TipRequestSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        showToast("添加成功")
                        getViewer()?.addGoodsSuc()
                        chooseTypePopUpWindow?.dismiss()
                    }
                })
    }
}