package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.MiniStoreGoodsInfoBaen
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-06-30
 */
interface MiniStoreGoodsInfoViewer: Viewer {
    fun setGoodsInfoList(list: List<MiniStoreGoodsInfoBaen>)
}