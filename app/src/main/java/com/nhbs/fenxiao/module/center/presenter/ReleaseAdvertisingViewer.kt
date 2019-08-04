package com.nhbs.fenxiao.module.center.presenter

import com.nhbs.fenxiao.module.center.bean.Row
import com.yu.common.mvp.Viewer

interface ReleaseAdvertisingViewer : Viewer {
  fun setReleaseAdvertisingImage(url: ArrayList<String>)

  fun setType(rows: List<Row>)
}
