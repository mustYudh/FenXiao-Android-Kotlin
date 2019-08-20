package com.nhbs.fenxiao.module.center.presenter

import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.yu.common.mvp.Viewer


interface ReleaseGoodsViewer : Viewer {
  fun setReleaseGoodsImage(url: ArrayList<String>)

fun getGoodsInfo(info: GoodsInfoBean?)



}
