package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-06-30
 */
interface MiniStoreActivityInfoViewer: Viewer {
    fun setActivityInfoList(list: List<MiniStoreActivityBean>?)
}