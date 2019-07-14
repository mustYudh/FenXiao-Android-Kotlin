package com.nhbs.fenxiao.module.center.presenter

import com.yu.common.mvp.Viewer

interface ReleaseAdvertisingViewer : Viewer {
  fun setReleaseAdvertisingImage(url: ArrayList<String>)
}
