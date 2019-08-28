package com.nhbs.fenxiao.module.store.pop

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudenghao
 * @date 2019-08-28
 */
class CreateNewTypePopUpWindow(context: Context) : BasePopupWindow(context,
    View.inflate(context, R.layout.create_new_type__pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {

  override fun getBackgroundShadow(): View {
    return bindView(R.id.root)
  }

  override fun getContainer(): View {
    return bindView(R.id.container)
  }

  fun setCancelListener(listener: View.OnClickListener) : CreateNewTypePopUpWindow{
      bindView<TextView>(R.id.cancel,listener)
    return this
  }

  fun setAgreeListener(listener: View.OnClickListener): CreateNewTypePopUpWindow {
    bindView<TextView>(R.id.ok,listener)
    return this
  }
}