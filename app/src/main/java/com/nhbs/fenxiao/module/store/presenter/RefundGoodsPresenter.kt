package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.xuexiang.xhttp2.XHttpProxy
import com.yu.common.framework.BaseViewPresenter

/**
 * @author myx
 * @date on 2019-11-24
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
@SuppressLint("CheckResult")
class RefundGoodsPresenter(viewer: RefundGoodsViewer) : BaseViewPresenter<RefundGoodsViewer>(
        viewer) {

    fun findExp(expressNumber: OrderInfo) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .findExp(expressNumber.expressNumber)
                .subscribeWith(object : LoadingRequestSubscriber<ExpInfoBean>(activity,false) {
                    override fun onSuccess(data: ExpInfoBean?) {
                        data?.orderInfo = expressNumber
                        getViewer()?.findExpSuccess(data)
                    }
                })

    }

    fun confirmRefund(info: OrderInfo) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .confirmRefund(info.id)
                .subscribeWith(object : LoadingRequestSubscriber<Any>(activity,false) {
                    override fun onSuccess(data: Any?) {
                        getViewer()?.confirmRefundSuc()
                    }
                })

    }

    fun refuseRefund(info: OrderInfo) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .refuseRefund(info.id)
                .subscribeWith(object : LoadingRequestSubscriber<Any>(activity,false) {
                    override fun onSuccess(data: Any?) {
                        getViewer()?.refuseRefundSuc()
                    }
                })

    }

    fun getRefundInfo(info: OrderInfo) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .getRefundInfo(info.id)
                .subscribeWith(object : LoadingRequestSubscriber<Any>(activity,false) {
                    override fun onSuccess(data: Any?) {
                    }
                })

    }

}