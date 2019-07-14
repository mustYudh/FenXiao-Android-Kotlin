package com.nhbs.fenxiao.module.center

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.nhbs.fenxiao.R
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudenghao
 * @date 2019-07-15
 */
class SetPrizePopupWindow(context: Context) : BasePopupWindow(
    context,
    View.inflate(context, R.layout.set_prize_pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
), View.OnClickListener {

  init {
    isFocusable = true
    bindView<View>(R.id.background).setOnClickListener {
      dismiss()
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

}