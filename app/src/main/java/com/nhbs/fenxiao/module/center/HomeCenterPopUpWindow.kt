package com.nhbs.fenxiao.module.center

import android.app.Activity
import android.os.Build
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.home.StatusBarColorManager
import com.nhbs.fenxiao.utils.DensityUtils
import com.yu.common.navigation.StatusBarFontColorUtil
import com.yu.common.windown.BasePopupWindow


/**
 * @author yudenghao
 * @date 2019-06-25
 */
class HomeCenterPopUpWindow(context: Activity) : BasePopupWindow(context,
    View.inflate(context, R.layout.home_center_pop_up_window_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
  var activity: Activity? = null

  init {
    isClippingEnabled = false
    activity = context
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
    var imageViewBg = bindView<ImageView>(R.id.bg)

  }


  override fun dismiss() {
    if (StatusBarColorManager.isDark) {
      StatusBarFontColorUtil.StatusBarLightMode(activity)
    } else {
      StatusBarFontColorUtil.statusBarDarkMode(activity)
    }
    super.dismiss()
  }

  override fun getBackgroundShadow(): View? {
    return null
  }

  override fun getContainer(): View? {
    return null
  }


  override fun showPopupWindow() {
    StatusBarFontColorUtil.statusBarDarkMode(activity!!)
    super.showPopupWindow()
  }

}