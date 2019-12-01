package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.yu.common.mvp.Viewer

interface NotSetTypeViewer : Viewer {
    fun setGoodsInfoList(list: List<GoodsListBean.GoodsInfoBean>?)
    fun showChooseTypeDialog(classListTOS: List<ClassTOS>)
    fun addGoodsSuc()
}

