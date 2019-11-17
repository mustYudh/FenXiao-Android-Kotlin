package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.nhbs.fenxiao.module.store.bean.TypeCountListBean
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttpProxy
import com.yu.common.framework.BaseViewPresenter

@SuppressLint("CheckResult")
class TypeManagerPresenter(viewer: TypeManagerViewer) : BaseViewPresenter<TypeManagerViewer>(viewer) {
    fun getGoodsCount() {
        XHttpProxy.proxy(AppApiServices::class.java)
                .getGoodsTypeCount()
                .subscribeWith(object : TipRequestSubscriber<TypeCountListBean>() {
                    override fun onSuccess(data: TypeCountListBean?) {
                        val list = ArrayList<ClassTOS>()
                        var count = 0
                        if (data != null) {
                            for (item in data.classListTOS) {
                                if (item.classify != "未分类") {
                                    list.add(item)
                                } else {
                                    count = item.total
                                }
                            }
                        }

                        getViewer()?.setGoodsTypeCount(list,count)
                    }

                })
    }
    fun createNewTypeName(name: String) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .merchandiseClass(name)
                .subscribeWith(object : TipRequestSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        showToast("创建成功")
                        getGoodsCount()
                    }

                })
    }
    fun changeTypeName(id: String,name: String) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .changeTypeName(id, name)
                .subscribeWith(object : TipRequestSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        showToast("修改成功")
                        getGoodsCount()
                    }

                })
    }
    fun deleteType(id: String) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .deleteType(id)
                .subscribeWith(object : TipRequestSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        showToast("删除成功")
                        getGoodsCount()
                    }

                })
    }

}