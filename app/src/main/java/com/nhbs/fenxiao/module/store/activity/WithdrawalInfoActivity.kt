package com.nhbs.fenxiao.module.store.activity

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.WithdrawalInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.WithdrawalInfoViewer
import com.yu.common.mvp.PresenterLifeCycle

class WithdrawalInfoActivity : BaseBarActivity(), WithdrawalInfoViewer {

  @PresenterLifeCycle
  internal var presenter = WithdrawalInfoPresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_withdrawal_info_view)
  }

  override fun loadData() {
    setTitle("提现详情")
  }
}
