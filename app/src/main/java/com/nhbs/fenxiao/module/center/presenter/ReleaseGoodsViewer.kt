package com.nhbs.fenxiao.module.center.presenter

import com.yu.common.mvp.Viewer


interface ReleaseGoodsViewer : Viewer {
  fun setReleaseGoodsImage(url: List<String>)
}
