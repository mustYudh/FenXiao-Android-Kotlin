package com.nhbs.fenxiao.module.store.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.nhbs.fenxiao.module.store.bean.RefundGoodsInfoBean
import com.nhbs.fenxiao.module.store.bean.RefundSucEvent
import com.nhbs.fenxiao.module.store.pop.CreateNewTypePopUpWindow
import com.nhbs.fenxiao.module.store.presenter.RefundGoodsPresenter
import com.nhbs.fenxiao.module.store.presenter.RefundGoodsViewer
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_refund_goods.*
import org.greenrobot.eventbus.EventBus

class RefundGoodsActivity : BaseBarActivity(), RefundGoodsViewer {

  @PresenterLifeCycle
  internal var presenter = RefundGoodsPresenter(this)
  private var goods_info : OrderInfo? = null
  private var position : Int = -1
  private var createNewTypePop: CreateNewTypePopUpWindow? = null


  companion object {
    private const val GOODS_INFO = "goods_info"
    private const val POSITION = "position"
    fun getIntent(context: Context, goods_info: OrderInfo,position: Int): Intent {
      val intent = Intent(context, RefundGoodsActivity::class.java)
      intent.putExtra(GOODS_INFO,goods_info)
      intent.putExtra(POSITION,position)
      return intent
    }
  }

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_refund_goods)
  }

  override fun loadData() {
    setTitle("退款")
    goods_info = intent.getSerializableExtra(GOODS_INFO) as OrderInfo?
    position = intent.getIntExtra(POSITION, -1)
    text_deliver.setOnClickListener {
      presenter.findExp(goods_info!!)
    }
    text_refuse.setOnClickListener {
      createNewTypePop = CreateNewTypePopUpWindow(activity)
      with(createNewTypePop!!) {
        setCancelListener(android.view.View.OnClickListener {
          dismiss()
        })
        setAgreeListener(android.view.View.OnClickListener {
          if (getInputInfo().checkTextEmpty()) {
            showToast("请输入拒绝原因")
          } else {
            presenter.refuseRefund(goods_info!!,getInputInfo())
            dismiss()
          }

        })
      }
      createNewTypePop!!.setTitle("拒绝原因")
      createNewTypePop!!.setHint("请填写拒绝原因")
      createNewTypePop!!.showPopupWindow()

    }
    text_confirm.setOnClickListener {
      presenter.confirmRefund(goods_info!!)
    }
    presenter.getRefundInfo(goods_info!!)
  }

  override fun getInfoSuc(data: RefundGoodsInfoBean) {
    text_refund_type.text = "退货类型: " + data.remark
    text_refund_reason.text = "退货原因: " + data.reason
    text_time.text = "发货时间: " + data.createTime
  }

  override fun findExpSuccess(data: ExpInfoBean?) {
    launchHelper.startActivity(DeliveryInfoActivity.getIntent(activity,data))
  }

  override fun confirmRefundSuc() {
    showToast("同意退款成功")
    val event = RefundSucEvent(goods_info)
    EventBus.getDefault().post(event)
    finish()
  }

  override fun refuseRefundSuc() {
    showToast("拒绝退款成功")
    val event = RefundSucEvent(goods_info)
    EventBus.getDefault().post(event)
    finish()
  }

}
