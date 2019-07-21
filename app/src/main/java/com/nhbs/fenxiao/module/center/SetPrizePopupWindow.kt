package com.nhbs.fenxiao.module.center

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.utils.selectPhoto
import com.shehuan.niv.NiceImageView
import com.yu.common.glide.ImageLoader
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudenghao
 * @date 2019-07-15
 */
class SetPrizePopupWindow(context: Activity) : BasePopupWindow(
    context,
    View.inflate(context, R.layout.set_prize_pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
), View.OnClickListener {


  init {
    isFocusable = true
    bindView<View>(R.id.background).setOnClickListener {
      dismiss()
    }
    bindView<NiceImageView>(R.id.add_photo).setOnClickListener {
      context.selectPhoto(1)
    }
  }

  override fun getBackgroundShadow(): View {
    return bindView(R.id.root)
  }

  override fun getContainer(): View {
    return bindView(R.id.container)
  }


  override fun onClick(v: View?) {

  }


  fun setSelectImageView(url: String) {
    ImageLoader.getInstance().displayImage(bindView(R.id.add_photo), url)
  }


}