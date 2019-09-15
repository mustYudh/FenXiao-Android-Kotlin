package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.yu.common.mvp.Viewer

interface SetTypeViewer : Viewer {
  fun setGoodsTypeCount(classListTOS: List<ClassTOS>,notTypeCount: Int)
}
