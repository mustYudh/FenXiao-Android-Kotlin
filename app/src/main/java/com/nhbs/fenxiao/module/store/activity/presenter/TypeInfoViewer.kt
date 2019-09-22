package com.nhbs.fenxiao.module.store.activity.presenter

import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean.RowsBean
import com.yu.common.mvp.Viewer

interface TypeInfoViewer : Viewer {
  abstract fun getMerchandiseClassListSuccess(rows: List<RowsBean>? = null)

}
