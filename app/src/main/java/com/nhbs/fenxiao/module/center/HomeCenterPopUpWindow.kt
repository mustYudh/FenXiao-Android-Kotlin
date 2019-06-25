package com.nhbs.fenxiao.module.center

import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.utils.DensityUtils
import com.yu.common.windown.BasePopupWindow


/**
 * @author yudenghao
 * @date 2019-06-25
 */
class HomeCenterPopUpWindow(context: Context) : BasePopupWindow(context,
    View.inflate(context, R.layout.home_center_pop_up_window_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
  init {
    isClippingEnabled = false
    this.height = DensityUtils.getDisplayHeight(getContext())
    if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      try {
        val mLayoutInScreen = PopupWindow::class.java.getDeclaredField("mLayoutInScreen")
        mLayoutInScreen.isAccessible = true
        mLayoutInScreen.set(this, true)
      } catch (e: NoSuchFieldException) {
        e.printStackTrace()
      } catch (e: IllegalAccessException) {
        e.printStackTrace()
      }

    }
  }
  override fun getBackgroundShadow(): View? {
    return null
  }

  override fun getContainer(): View? {
    return null
  }

}