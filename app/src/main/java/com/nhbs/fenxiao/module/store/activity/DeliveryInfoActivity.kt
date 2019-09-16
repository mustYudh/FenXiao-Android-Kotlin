package com.nhbs.fenxiao.module.store.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.DeliveryInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.DeliveryInfoViewer
import com.nhbs.fenxiao.module.store.adapter.DeliveryInfoAdapter
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.yu.common.glide.ImageLoader
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_delivery_info_view.delivery_company
import kotlinx.android.synthetic.main.activity_delivery_info_view.delivery_order
import kotlinx.android.synthetic.main.activity_delivery_info_view.delivery_status
import kotlinx.android.synthetic.main.activity_delivery_info_view.delivery_time
import kotlinx.android.synthetic.main.activity_delivery_info_view.empty_view
import kotlinx.android.synthetic.main.activity_delivery_info_view.list
import kotlinx.android.synthetic.main.activity_delivery_info_view.show_photo

class DeliveryInfoActivity : BaseBarActivity(), DeliveryInfoViewer {


  @PresenterLifeCycle
  private var presenter = DeliveryInfoPresenter(this)
  private val adapter = DeliveryInfoAdapter()

  companion object {
    private const val DELIVERY_INFO = "DELIVERY_INFO"
    fun getIntent(context: Context?, info: ExpInfoBean?): Intent {
      val intent = Intent(context, DeliveryInfoActivity::class.java)
      intent.putExtra(DELIVERY_INFO, info)
      return intent
    }
  }


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_delivery_info_view)
  }


  @SuppressLint("SetTextI18n")
  override fun loadData() {
    setTitle("物流信息")
    val info = intent.getSerializableExtra(DELIVERY_INFO) as ExpInfoBean?
    if (info != null) {
      val orderInfo = info.orderInfo
      val images = orderInfo?.goodsImage?.split(",")
      if (images != null && images.size > 0) {
        val url = images[0]
        ImageLoader.getInstance().displayImage(show_photo, url, R.drawable.ic_placeholder,
            R.drawable.ic_placeholder)
      }
      bindText<TextView>(R.id.content, orderInfo.title)
      val tags = ArrayList<String>()
      if (!TextUtils.isEmpty(orderInfo.tagOne)) {
        tags.add(orderInfo.tagOne)
      }
      if (!TextUtils.isEmpty(orderInfo.tagTwo)) {
        tags.add(orderInfo.tagTwo)
      }
      var tag = ""
      tags.forEachIndexed { index, str ->
        tag += "$str${if (index < tags.size - 1) "," else ""}"
      }
      if (!TextUtils.isEmpty(tag)) {
        bindText<TextView>(R.id.type, tag)
      }
      bindText<TextView>(R.id.money, orderInfo.totalPrice)
      bindText<TextView>(R.id.count, "X${orderInfo.number}")
    }
    delivery_status.text = "物流状态：${if (info?.status == 4) "已签收" else "未签收"}"
    delivery_company.text = "承运公司：${info?.expTextName}"
    delivery_order.text = "运单编号：${info?.mailNo}"
    delivery_time.text = "发货时间：${info?.updateStr}"
    if (info?.data != null && info?.data.size > 0) {
      list.setLinearLayoutAdapter(adapter,true)
      adapter.setNewData(info.data)
      empty_view.visibility = View.GONE
      list.visibility = View.VISIBLE
    } else {
      empty_view.visibility = View.VISIBLE
      list.visibility = View.GONE
    }

  }
}
