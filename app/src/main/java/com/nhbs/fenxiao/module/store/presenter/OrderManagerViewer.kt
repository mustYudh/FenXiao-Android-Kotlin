package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.OrderCountBean
import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-09-14
 */
interface OrderManagerViewer : Viewer {

  fun setOrdersCount(count: OrderCountBean?)

  fun getGoodsInfo(rows: List<OrderInfo>,type: Int,position: Int)

  fun goSendGoodsSuccess(info: OrderInfo,position: Int)

  fun updateOrderPriceSuccess(info: OrderInfo,position: Int)
}