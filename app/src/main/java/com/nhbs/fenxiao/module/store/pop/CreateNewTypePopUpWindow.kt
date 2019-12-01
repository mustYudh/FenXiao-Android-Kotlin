package com.nhbs.fenxiao.module.store.pop

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.utils.getInputText
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudenghao
 * @date 2019-08-28
 */
class CreateNewTypePopUpWindow(context: Context) : BasePopupWindow(context,
    View.inflate(context, R.layout.create_new_type__pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {

  init {
    isFocusable = true
  }

  fun getInputInfo(): String {
    return bindView<EditText>(R.id.name).getInputText()
  }

  fun setHint(hint: String){
    bindView<EditText>(R.id.name).hint = hint
  }

  override fun getBackgroundShadow(): View {
    return bindView(R.id.root)
  }

  override fun getContainer(): View {
    return bindView(R.id.container)
  }

  fun setTitle(title: String){
    bindView<TextView>(R.id.text_title).text = title
  }

  fun setCancelListener(listener: View.OnClickListener): CreateNewTypePopUpWindow {
    bindView<TextView>(R.id.cancel, listener)
    return this
  }

  fun setAgreeListener(listener: View.OnClickListener): CreateNewTypePopUpWindow {
    bindView<TextView>(R.id.ok, listener)
    return this
  }
}