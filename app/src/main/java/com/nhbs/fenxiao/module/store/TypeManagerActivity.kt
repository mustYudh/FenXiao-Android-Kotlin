package com.nhbs.fenxiao.module.store

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.presenter.TypeManagerPresenter
import com.nhbs.fenxiao.module.store.presenter.TypeManagerViewer
import com.yu.common.mvp.PresenterLifeCycle

class TypeManagerActivity : BaseBarActivity(), TypeManagerViewer {

  @PresenterLifeCycle
  internal var presenter = TypeManagerPresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_type_manager_view)
  }

  override fun loadData() {

  }
}
