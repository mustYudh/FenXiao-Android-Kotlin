package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.presenter.AuditRecordsPresenter
import com.nhbs.fenxiao.module.store.presenter.AuditRecordsViewer
import com.yu.common.mvp.PresenterLifeCycle

/**
 * @author yudneghao
 * @date 2019-08-17
 */
class AuditRecordsFragment : BaseFragment(),AuditRecordsViewer {
@PresenterLifeCycle
private var mPresenter = AuditRecordsPresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
  }

  override fun loadData() {
  }

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_goods_layout
  }
}