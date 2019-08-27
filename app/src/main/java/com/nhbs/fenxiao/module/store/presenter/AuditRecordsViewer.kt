package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-08-17
 */
interface AuditRecordsViewer : Viewer {
  fun setRecordInfoList(list: List<MiniStoreActivityBean>?)
}