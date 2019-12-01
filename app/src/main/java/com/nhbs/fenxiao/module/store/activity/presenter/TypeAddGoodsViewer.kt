package com.nhbs.fenxiao.module.store.activity.presenter

import com.nhbs.fenxiao.module.store.bean.OtherTypeGoodsBean
import com.yu.common.mvp.Viewer

interface TypeAddGoodsViewer : Viewer {
    fun setOtherGoodsInfoList(list: List<OtherTypeGoodsBean.GoodsInfoBean>?)

    fun addGoodsSuc()
}
