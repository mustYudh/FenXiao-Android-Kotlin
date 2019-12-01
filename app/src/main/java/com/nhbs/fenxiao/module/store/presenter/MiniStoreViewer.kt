package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.ShopInfoBean
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-06-30
 */
interface MiniStoreViewer: Viewer {

  fun setShopInfo(info: ShopInfoBean?)

  fun needOpenStore()

  fun getUnReadMessageCont()
}