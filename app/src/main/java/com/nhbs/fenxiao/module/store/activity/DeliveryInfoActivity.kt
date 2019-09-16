package com.nhbs.fenxiao.module.store.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.DeliveryInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.DeliveryInfoViewer
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.yu.common.glide.ImageLoader
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_delivery_info_view.content
import kotlinx.android.synthetic.main.activity_delivery_info_view.count
import kotlinx.android.synthetic.main.activity_delivery_info_view.money
import kotlinx.android.synthetic.main.activity_delivery_info_view.show_photo
import kotlinx.android.synthetic.main.activity_delivery_info_view.type

class DeliveryInfoActivity : BaseBarActivity(), DeliveryInfoViewer {

  @PresenterLifeCycle
  var presenter = DeliveryInfoPresenter(this)

  companion object {
    private const val DELIVERY_INFO = "DELIVERY_INFO"
    fun getIntent(context: Context?,info: ExpInfoBean?): Intent {
      val intent = Intent(context,DeliveryInfoActivity::class.java)
      intent.putExtra(DELIVERY_INFO, info)
      return intent
    }
  }


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_delivery_info_view)
  }


  @SuppressLint("SetTextI18n")
  override fun loadData() {
    val info = intent.getSerializableExtra(DELIVERY_INFO) as ExpInfoBean?
    if (info != null) {
      val orderInfo = info.orderInfo
      val images = orderInfo?.goodsImage?.split(",")
      if (images != null && images.size > 0) {
        val url = images[0]
        ImageLoader.getInstance().displayImage(show_photo, url, R.drawable.ic_placeholder,
            R.drawable.ic_placeholder)
      }
      content.text = orderInfo.title
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
      if(!TextUtils.isEmpty(tag)) {
        type.text = tag
      }
      money.text = orderInfo.totalPrice
      count.text = "X${orderInfo.number}"
    }
//    delivery_status.text = ""
  }
}
