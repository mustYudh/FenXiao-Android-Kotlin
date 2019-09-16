package com.nhbs.fenxiao.module.store.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.ExpData


/**
 * @author yudneghao
 * @date 2019-09-16
 */
class DeliveryInfoAdapter : BaseQuickAdapter<ExpData, BaseViewHolder>(
    R.layout.item_delivery_info_layout, null) {
  override fun convert(helper: BaseViewHolder?, item: ExpData?) {
    helper?.setText(R.id.info, item?.context)
    val point = helper?.getView<View>(R.id.point)
    val line = helper?.getView<View>(R.id.line)
    val context = helper?.getView<TextView>(R.id.info)
    point?.isSelected = helper?.position == 0
    context?.isSelected = helper?.position == 0
    if(helper?.position == itemCount - 1) {
      line?.visibility = View.GONE
    } else {
      line?.visibility = View.VISIBLE
    }

  }

}