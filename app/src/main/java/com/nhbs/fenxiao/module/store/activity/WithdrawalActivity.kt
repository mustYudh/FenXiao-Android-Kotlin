package com.nhbs.fenxiao.module.store.activity

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.presenter.WithdrawalPresenter
import com.nhbs.fenxiao.module.store.presenter.WithdrawalViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_login_view.next_action

class WithdrawalActivity : BaseBarActivity(), WithdrawalViewer {

  @PresenterLifeCycle
  internal var presenter = WithdrawalPresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_withdrawal_view)
  }

  override fun loadData() {
    setTitle("提现")
    next_action.setOnClickListener {
        launchHelper.startActivity(WithdrawalInfoActivity::class.java)
    }
  }
}
