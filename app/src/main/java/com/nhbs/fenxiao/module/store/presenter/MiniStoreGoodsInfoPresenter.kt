package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.MiniStoreGoodsInfoBaen
import com.yu.common.framework.BaseViewPresenter

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreGoodsInfoPresenter(viewer: MiniStoreGoodsInfoViewer) :
    BaseViewPresenter<MiniStoreGoodsInfoViewer>(viewer) {

    fun getGoodsInfoList() {
        val list = ArrayList<MiniStoreGoodsInfoBaen>()
        for (i in 0..20) {
            list.add(MiniStoreGoodsInfoBaen())
        }
        getViewer()?.setGoodsInfoList(list)
    }
}