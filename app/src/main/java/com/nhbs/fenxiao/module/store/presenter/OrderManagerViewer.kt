package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-09-14
 */
interface OrderManagerViewer : Viewer {
  fun getGoodsInfo(rows: List<OrderInfo>?)
}