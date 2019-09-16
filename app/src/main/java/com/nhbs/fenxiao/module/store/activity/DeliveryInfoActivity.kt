package com.nhbs.fenxiao.module.store.activity

import android.content.Intent
import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.DeliveryInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.DeliveryInfoViewer
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.yu.common.glide.ImageLoader
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_delivery_info_view.show_photo

class DeliveryInfoActivity : BaseBarActivity(), DeliveryInfoViewer {

  @PresenterLifeCycle
  var presenter = DeliveryInfoPresenter(this)

  companion object {
    private const val DELIVERY_INFO = "DELIVERY_INFO"
    fun getIntent(info: ExpInfoBean): Intent {
      val intent = Intent()
      intent.putExtra(DELIVERY_INFO, info)
      return intent
    }
  }


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_delivery_info_view)
  }


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
    }

  }
}
