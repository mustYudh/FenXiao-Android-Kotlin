package com.nhbs.fenxiao.module.store.pop

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.utils.getInputText
import com.nhbs.fenxiao.utils.setfilters
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudneghao
 * @date 2019-09-15
 */
class EditPricePopupWindow(context: Context,result: (goodsPrice: String,packagePrice: String) -> Unit) : BasePopupWindow(context,
    View.inflate(context, R.layout.order_change_price_pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {

  init {
    var isFree = false
    val cancel = bindView<ImageView>(R.id.cancel)
    cancel.setOnClickListener {
      dismiss()
    }
    val commit = bindView<TextView>(R.id.commit)
    val packagePrice = bindView<EditText>(R.id.package_price)
    val price = bindView<EditText>(R.id.price)
    packagePrice.setfilters()
    price.setfilters()
    commit.setOnClickListener {
      result(price.getInputText(),packagePrice.getInputText())
    }
    val free = bindView<ImageView>(R.id.free)
    val freePackage = bindView<LinearLayout>(R.id.free_package)
    freePackage.setOnClickListener {
      isFree = !isFree
      free.isSelected = isFree
      if (isFree) {
        packagePrice.setText("")
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