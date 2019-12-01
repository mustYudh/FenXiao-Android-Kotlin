package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.GoodsTypeBean
import com.nhbs.fenxiao.module.center.bean.ReleaseAdParams
import com.nhbs.fenxiao.module.center.bean.ReleaseAdesultBean
import com.nhbs.fenxiao.module.order.bean.PayInfo
import com.nhbs.fenxiao.utils.DialogUtils
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.oss.UploadUtils
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.XHttpProxy
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.ui.DelayClickTextView
import com.yu.common.utils.RxSchedulerUtils

@SuppressLint("CheckResult")
class ReleaseAdvertisingPresenter(
        viewer: ReleaseAdvertisingViewer) : BaseViewPresenter<ReleaseAdvertisingViewer>(viewer) {
    private var payDialog: DialogUtils? = null


    fun addNewPhoto(url: ArrayList<String>) {
        getViewer()?.setReleaseAdvertisingImage(url)
    }


    fun getAdType() {
        XHttpProxy.proxy(AppApiServices::class.java)
                .getAdType()
                .subscribeWith(object : LoadingRequestSubscriber<GoodsTypeBean>(activity!!, false) {
                    override fun onSuccess(goods: GoodsTypeBean?) {
                        goods?.rows?.forEach {
                            it.classify = it.name
                        }
                        getViewer()?.setType(goods?.rows!!)
                    }
                })
    }


    fun releaseAD(params: ReleaseAdParams, url: ArrayList<String>) {
        if (params.checkParams()) {
            UploadUtils.uploadFile(activity, url, "Fa_Bu_AD", "png") { fileList ->
                params.imgs = ""
                fileList.forEachIndexed { index, result ->
                    params.imgs += "$result${if (index < fileList.size - 2) "," else ""}"
                }
                commit(params)
            }
        }
    }

    private var type = 0

    private fun commit(params: ReleaseAdParams) {
        XHttp.custom(AppApiServices::class.java)
                .releaseAD(HttpUtils.getJsonRequestBody(params))
                .compose(RxSchedulerUtils._io_main_o<ApiResult<ReleaseAdesultBean>>())
                .subscribeWith(object : LoadingRequestSubscriber<ApiResult<ReleaseAdesultBean>>(activity, false) {
                    override fun onSuccess(t: ApiResult<ReleaseAdesultBean>?) {
                        if (t?.code == 2000) {

                            payDialog = DialogUtils.Builder(activity).view(R.layout.dialog_pay)
                                    .gravity(Gravity.BOTTOM)
                                    .cancelTouchout(true)
                                    .addViewOnclick(R.id.iv_close) { view: View? ->
                                        if (payDialog!!.isShowing) {
                                            payDialog!!.dismiss()
                                        }
                                    }
                                    .style(R.style.Dialog)
                                    .build()
                            payDialog?.show()

                            val rl_ali = payDialog?.findViewById<RelativeLayout>(R.id.rl_ali)
                            val rl_wx = payDialog?.findViewById<RelativeLayout>(R.id.rl_wx)
                            val iv_ali = payDialog?.findViewById<ImageView>(R.id.iv_ali)
                            val iv_wx = payDialog?.findViewById<ImageView>(R.id.iv_wx)
                            val tv_price = payDialog?.findViewById<TextView>(R.id.tv_price)
//              tv_price?.text = "¥" + createUserOrderBean.data.price
                            rl_ali?.setOnClickListener { view: View? ->
                                iv_ali?.setImageResource(R.drawable.ic_circle_select)
                                iv_wx?.setImageResource(R.drawable.ic_circle_normal)
                                type = 0
                            }

                            rl_wx?.setOnClickListener { view: View? ->
                                iv_wx?.setImageResource(R.drawable.ic_circle_select)
                                iv_ali?.setImageResource(R.drawable.ic_circle_normal)
                                type = 1
                            }

                            val tv_commit: DelayClickTextView = payDialog?.findViewById(R.id.tv_commit)!!
                            tv_commit.setOnClickListener { view: View? -> userToPay(t.data.id, type) }
//              activity?.finish()
                        } else if (!t?.msg.checkTextEmpty()) {
                            showToast(t?.msg!!)
                        }
                    }
                })
    }


    fun userToPay(id: String, type: Int) {
        XHttpProxy.proxy(AppApiServices::class.java)
                .getAdOrder(id, type).safeSubscribe(object : LoadingRequestSubscriber<PayInfo>(activity!!,false) {
                    override fun onSuccess(t: PayInfo?) {

                    }

                })
    }
}
