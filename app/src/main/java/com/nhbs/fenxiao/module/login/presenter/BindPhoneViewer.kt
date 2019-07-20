package com.nhbs.fenxiao.module.login.presenter

import com.yu.common.mvp.Viewer


interface BindPhoneViewer : Viewer {
  fun sendVerCodeSuccess(time : Long)
}
