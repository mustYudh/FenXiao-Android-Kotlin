package com.nhbs.fenxiao.module.store.pop

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.nhbs.fenxiao.utils.getInputText
import com.nhbs.fenxiao.utils.getMoney
import com.nhbs.fenxiao.utils.setfilters
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudneghao
 * @date 2019-09-15
 */
class EditPricePopupWindow(context: Context, orderInfo: OrderInfo,
    result: (goodsPrice: String, packagePrice: String) -> Unit) : BasePopupWindow(context,
    View.inflate(context, R.layout.order_change_price_pop_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {

  init {
    isFocusable = true
    var isFree = false
    val cancel = bindView<ImageView>(R.id.cancel)
    cancel.setOnClickListener {
      dismiss()
    }
    val commit = bindView<TextView>(R.id.commit)
    val packagePrice = bindView<EditText>(R.id.package_price)
    val price = bindView<EditText>(R.id.price)
    val free = bindView<ImageView>(R.id.free)
    packagePrice.setfilters()
    price.setfilters()
    price.setText(orderInfo.price.getMoney())
    packagePrice.setText(orderInfo.postage)
    price.setSelection(price.getInputText().length)
    packagePrice.setSelection(packagePrice.getInputText().length)
    if (TextUtils.isEmpty(packagePrice.getInputText()) || packagePrice.getInputText() == "0.00") {
      isFree = true
      free.isSelected = isFree
    }
    commit.setOnClickListener {
      if (TextUtils.isEmpty(price.getInputText())) {
        showToast("价格输入不能为空")
      } else {
        if (isFree) {
          result(price.getInputText(), packagePrice.getInputText())
        } else {
          if (TextUtils.isEmpty(packagePrice.getInputText())) {
            showToast("请输入邮费")
          } else {
            result(price.getInputText(), packagePrice.getInputText())
          }
        }
      }
      dismiss()
    }

    packagePrice.addTextChangedListener(object: TextWatcher {
      override fun afterTextChanged(s: Editable?) {

      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
        isFree = false
        free.isSelected = isFree
      }

    })

    val freePackage = bindView<LinearLayout>(R.id.free_package)
    freePackage.setOnClickListener {
      isFree = !isFree
      free.isSelected = isFree
      if (isFree) {
        packagePrice.setText("0.00")
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