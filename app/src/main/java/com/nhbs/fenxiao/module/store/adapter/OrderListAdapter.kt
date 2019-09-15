package com.nhbs.fenxiao.module.store.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.OrderInfo

/**
 * @author yudneghao
 * @date 2019-08-18
 */
class OrderListAdapter(var status: Int) : BaseQuickAdapter<OrderInfo, BaseViewHolder>(
    (R.layout.item_order_list_layout), null) {
  override fun convert(helper: BaseViewHolder?, item: OrderInfo?) {
    helper?.setText(R.id.name, "收货人: ${item?.userName} ${item?.mobile}")?.setText(R.id.time,
        "支付时间: ${item?.receivingTime}")?.setText(R.id.price, "本单金额：¥${item?.totalPrice}")
        ?.setText(R.id.count, "共${item?.number}件")
    val statusText = helper?.getView<TextView>(R.id.status)
    when (status) {
      0 -> {
        helper?.setVisible(R.id.refund_rood, false)
        helper?.setText(R.id.status_btn, "发货")
        statusText?.setTextColor(Color.parseColor("#FF2751"))
        statusText?.text = "待收货"
      }
      1 -> {
        helper?.setVisible(R.id.refund_rood, false)
        helper?.setText(R.id.status_btn, "修改价格")
        statusText?.setTextColor(Color.parseColor("#FF2751"))
        statusText?.text = "待收货"
      }
      2 -> {
        helper?.setVisible(R.id.refund_rood, false)
        helper?.setText(R.id.status_btn, "查看物流")
        statusText?.setTextColor(Color.parseColor("#A0A9BB"))
        statusText?.text = "已发货"
      }
      3 -> {
        helper?.setVisible(R.id.refund_rood, true)
        helper?.setText(R.id.status_btn, "处理退款")
        statusText?.setTextColor(Color.parseColor("#FF2751"))
        statusText?.text = "待收货"
      }
    }
  }

}