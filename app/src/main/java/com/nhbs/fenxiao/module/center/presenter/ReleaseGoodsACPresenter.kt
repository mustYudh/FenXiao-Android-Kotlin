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
import com.nhbs.fenxiao.module.center.bean.ReleaseActivityParams
import com.nhbs.fenxiao.utils.DialogUtils
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.ui.DelayClickTextView
import com.yu.common.utils.RxSchedulerUtils


@SuppressLint("CheckResult")
class ReleaseGoodsACPresenter(
    viewer: ReleaseGoodsACActivityViewer) : BaseViewPresenter<ReleaseGoodsACActivityViewer>(
    viewer) {
    private var payDialog: DialogUtils? = null

    private var type = 1

    fun releaseActivity(params: ReleaseActivityParams) {
    if (params.checkParams()) {
      XHttp.custom(AppApiServices::class.java)
          .releaseActivity(HttpUtils.getJsonRequestBody(params))
          .compose(RxSchedulerUtils._io_main_o<ApiResult<Any>>())
          .subscribeWith(object : LoadingRequestSubscriber<ApiResult<Any>>(activity, false) {
            override fun onSuccess(t: ApiResult<Any>?) {
              if (t?.code == 2000) {
//                showToast("发布成功")
//                activity?.finish()

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
//                  tv_price?.text = "¥" + createUserOrderBean.data.price
                  rl_ali?.setOnClickListener { view: View? ->
                      iv_ali?.setImageResource(R.drawable.ic_circle_select)
                      iv_wx?.setImageResource(R.drawable.ic_circle_normal)
                      type = 1
                  }

                  rl_wx?.setOnClickListener { view: View? ->
                      iv_wx?.setImageResource(R.drawable.ic_circle_select)
                      iv_ali?.setImageResource(R.drawable.ic_circle_normal)
                      type = 2
                  }

                  val tv_commit: DelayClickTextView = payDialog?.findViewById(R.id.tv_commit)!!
//                  tv_commit.setOnClickListener { view: View? -> toPay(t.data.id, "2", type.toString() + "", createUserOrderBean) }
              } else if (!t?.msg.checkTextEmpty()) {
                showToast(t?.msg!!)
              }
            }

          })
    }
  }
}