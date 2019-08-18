package com.nhbs.fenxiao.module.store.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.OrderListBean

/**
 * @author yudneghao
 * @date 2019-08-18
 */
class OrderListAdapter  : BaseQuickAdapter<OrderListBean, BaseViewHolder>(
    (R.layout.item_order_list_layout), null) {
  override fun convert(helper: BaseViewHolder?, item: OrderListBean?) {

  }

}