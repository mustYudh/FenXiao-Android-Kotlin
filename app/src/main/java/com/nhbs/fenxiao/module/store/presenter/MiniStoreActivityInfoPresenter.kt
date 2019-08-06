package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.yu.common.framework.BaseViewPresenter

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreActivityInfoPresenter(viewer: MiniStoreActivityInfoViewer) :
    BaseViewPresenter<MiniStoreActivityInfoViewer>(viewer) {

  fun getGoodsInfoList() {
    val list = ArrayList<MiniStoreActivityBean>()
    for (i in 0..20) {
      list.add(MiniStoreActivityBean())
    }
    getViewer()?.setGoodsInfoList(list)
  }
}