package com.nhbs.fenxiao.module.center

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.center.presenter.PrizeInfo
import com.nhbs.fenxiao.utils.getInputText
import com.nhbs.fenxiao.utils.selectPhoto
import com.shehuan.niv.NiceImageView
import com.yu.common.glide.ImageLoader
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudenghao
 * @date 2019-07-15
 */
class SetPrizePopupWindow(context: Activity, callBack: (info: PrizeInfo) -> Unit) : BasePopupWindow(
    context,
    View.inflate(context, R.layout.set_prize_pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
), View.OnClickListener {

  private var prizeRes = ""

  init {
    isFocusable = true
    bindView<View>(R.id.background).setOnClickListener {
      dismiss()
    }
    bindView<NiceImageView>(R.id.add_photo).setOnClickListener {
      context.selectPhoto(1)
    }

    bindView<TextView>(R.id.commit) {
      val info = PrizeInfo()
      info.prizeRes = prizeRes
      info.prizeName = bindView<EditText>(R.id.prize_name).getInputText()
      info.prizeCount = "${bindView<EditText>(R.id.prize_count).getInputText()}"
      if (info.checkEmpty()) {
        callBack(info)
        dismiss()
      }
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


  fun setInfo() {

  }



  fun setSelectImageView(url: String) {
    prizeRes = url
    ImageLoader.getInstance().displayImage(bindView(R.id.add_photo), url)
  }


}