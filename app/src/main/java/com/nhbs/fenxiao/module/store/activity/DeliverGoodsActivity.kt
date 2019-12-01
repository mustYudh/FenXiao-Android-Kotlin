package com.nhbs.fenxiao.module.store.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.mylhyl.circledialog.CircleDialog
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber
import com.nhbs.fenxiao.module.store.adapter.TypeManageListAdapter
import com.nhbs.fenxiao.module.store.bean.DeliverSucEvent
import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.nhbs.fenxiao.module.store.presenter.DeliverGoodsPresenter
import com.nhbs.fenxiao.module.store.presenter.DeliverGoodsViewer
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttpProxy
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_deliver_goods.*
import kotlinx.android.synthetic.main.item_classification_goods.*
import org.greenrobot.eventbus.EventBus

class DeliverGoodsActivity : BaseBarActivity(), DeliverGoodsViewer {

  @PresenterLifeCycle
  internal var presenter = DeliverGoodsPresenter(this)
  private val adapter = TypeManageListAdapter()
  private var goods_info : OrderInfo? = null
  private var expressNumber : String = ""
  private var dealWay : Int = 3
  private var position : Int = -1
  private var isChoose : Boolean = false
  private var isNeed : Boolean = true

  companion object {
    private const val GOODS_INFO = "goods_info"
    private const val POSITION = "position"
    fun getIntent(context: Context, goods_info: OrderInfo,position: Int): Intent {
      val intent = Intent(context, DeliverGoodsActivity::class.java)
      intent.putExtra(GOODS_INFO,goods_info)
      intent.putExtra(POSITION,position)
      return intent
    }
  }

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_deliver_goods)
  }

  override fun loadData() {
    setTitle("发货")
    goods_info = intent.getSerializableExtra(GOODS_INFO) as OrderInfo?
    position = intent.getIntExtra(POSITION, -1)
    val spannableString = SpannableString("发货")
    val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
    spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    setRightMenu(spannableString) {
      if (isChoose) {
        CircleDialog.Builder()
                .setTitle("温馨提示")
                .setText("\n确定要发货吗?\n")
                .setPositive("确定") {
                  XHttpProxy.proxy(AppApiServices::class.java)
                          .goSendGoods(goods_info!!.id, if (isNeed) edit_express.text.toString() else "" , dealWay)
                          .subscribeWith(object : TipRequestSubscriber<Any>() {
                            override fun onSuccess(t: Any?) {
                              val event = DeliverSucEvent(goods_info,position)
                              EventBus.getDefault().post(event)
                              finish()
                            }
                          })
                }.setNegative("取消" ) {

                }
                .show(activity.supportFragmentManager)
      }else {
        showToast("请选择商品")
      }
    }
    img_quick.isSelected = true
    ll_quick.visibility = View.VISIBLE
    ll_no_logistics.visibility = View.GONE
    ll_quick_btn.setOnClickListener {
      if (!img_quick.isSelected) {
        img_quick.isSelected = true
        img_no_logistics.isSelected = false
        ll_quick.visibility = View.VISIBLE
        ll_no_logistics.visibility = View.GONE
        dealWay = 3
        isNeed = true
      }
    }
    ll_no_logistics_btn.setOnClickListener {
      if (!img_no_logistics.isSelected) {
        img_quick.isSelected = false
        img_no_logistics.isSelected = true
        ll_quick.visibility = View.GONE
        ll_no_logistics.visibility = View.VISIBLE
        dealWay = 1
        isNeed = false
      }
    }
    ll_goods.setOnClickListener {
      if (img_choose.isSelected) {
        img_choose.isSelected = false
        isChoose = false
      }else {
        img_choose.isSelected = true
        isChoose = true
      }
    }
    text_user_name.text = "收货人: " + goods_info!!.userName + " " + goods_info!!.mobile
    text_address.text = goods_info!!.addressId
    text_price.text = "¥" + goods_info!!.price
    text_name.text = goods_info!!.title
    img_choose.visibility = View.VISIBLE

  }

}
