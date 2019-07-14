package com.nhbs.fenxiao.module.center.activity.presenter

import com.yu.common.mvp.Viewer

interface ReleaseAdvertisingViewer : Viewer {
  fun setReleaseAdvertisingImage(url: ArrayList<String>)
}
