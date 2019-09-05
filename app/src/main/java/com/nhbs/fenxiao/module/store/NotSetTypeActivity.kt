package com.nhbs.fenxiao.module.store

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.presenter.NotSetTypePresenter
import com.nhbs.fenxiao.module.store.presenter.NotSetTypeViewer
import com.yu.common.mvp.PresenterLifeCycle

class NotSetTypeActivity : BaseBarActivity(), NotSetTypeViewer {

  @PresenterLifeCycle
  internal var presenter = NotSetTypePresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_type_manager_view)
  }

  override fun loadData() {
    setTitle("未分类")
  }
}
