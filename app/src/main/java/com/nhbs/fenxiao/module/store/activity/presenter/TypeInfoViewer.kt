package com.nhbs.fenxiao.module.store.activity.presenter

import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.yu.common.mvp.Viewer

interface TypeInfoViewer : Viewer {

  fun setGoodsInfoList(list: List<GoodsInfoBean>?)

}
