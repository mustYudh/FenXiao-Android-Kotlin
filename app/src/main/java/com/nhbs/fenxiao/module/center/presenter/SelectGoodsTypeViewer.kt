package com.nhbs.fenxiao.module.center.presenter

import com.nhbs.fenxiao.module.center.bean.Row
import com.yu.common.mvp.Viewer

interface SelectGoodsTypeViewer : Viewer {
  fun setType(data: List<Row>?)
}