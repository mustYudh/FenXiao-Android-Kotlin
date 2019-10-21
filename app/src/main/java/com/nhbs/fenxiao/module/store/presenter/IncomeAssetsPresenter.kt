package com.nhbs.fenxiao.module.store.presenter

import android.annotation.SuppressLint
import com.nhbs.fenxiao.http.api.OtherApiServices
import com.nhbs.fenxiao.http.loading.NetLoadingDialog
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.mine.bean.BindWxBean
import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.exception.ApiException
import com.yu.common.framework.BaseViewPresenter

/**
 * @author yudneghao
 * @date 2019-10-21
 */
@SuppressLint("CheckResult")
class IncomeAssetsPresenter(viewer: IncomeAssetsViewer) : BaseViewPresenter<IncomeAssetsViewer>(viewer) {

    fun getUserInfo() {
        XHttpProxy.proxy(OtherApiServices::class.java)
                .userInfo()
                .subscribeWith(object : LoadingRequestSubscriber<MineUserInfoBean>(activity, false) {
                    override fun onSuccess(mineUserInfoBean: MineUserInfoBean) {
                        assert(getViewer() != null)
                        getViewer()!!.getUserInfoSuccess(mineUserInfoBean)
                    }
                })
    }


    fun boundWinXin(openId: String?) {
        XHttpProxy.proxy(OtherApiServices::class.java)
                .boundWinXin(openId)
                .subscribeWith(object : LoadingRequestSubscriber<BindWxBean>(activity, false) {
                    override fun onSuccess(bindWxBean: BindWxBean) {
                        assert(getViewer() != null)
                        getViewer()!!.boundWinXinSuccess()
                    }

                    override fun onError(apiException: ApiException?) {
                        super.onError(apiException)
                        NetLoadingDialog.dismissLoading()
                    }
                })
    }
}