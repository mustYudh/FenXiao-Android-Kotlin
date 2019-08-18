package com.nhbs.fenxiao.module.store.activity

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.id
import com.nhbs.fenxiao.R.layout
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.OrderManagerPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.OrderManagerViewer
import com.nhbs.fenxiao.module.store.fragment.IncomeAssetsFragment
import com.nhbs.fenxiao.module.store.fragment.OrderManagerFragment
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.order_manager_title_bar_layout.ic_come_assets
import kotlinx.android.synthetic.main.order_manager_title_bar_layout.order_manager

class OrderManagerActivity : BaseBarActivity(), OrderManagerViewer {

  override fun getActionBarLayoutId(): Int {
    return layout.order_manager_title_bar_layout
  }

  @PresenterLifeCycle
  internal var presenter = OrderManagerPresenter(this)


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(layout.activity_order_manager_view)
    initView()
    initListener()
  }

  private fun initListener() {
    order_manager.setOnClickListener {
      order_manager.isSelected = true
      ic_come_assets.isSelected = false
      order_manager.isEnabled = false
      ic_come_assets.isEnabled = true
      showFragment(OrderManagerFragment.getFragment(), id.root)
    }

    ic_come_assets.setOnClickListener {
      order_manager.isSelected = false
      ic_come_assets.isSelected = true
      order_manager.isEnabled = true
      ic_come_assets.isEnabled = false
      showFragment(IncomeAssetsFragment.getFragment(), id.root)
    }
  }

  private fun initView() {
    order_manager.isEnabled = false
    order_manager.isSelected = true
    ic_come_assets.isSelected = false
    showFragment(OrderManagerFragment.getFragment(), R.id.root)
  }


  override fun loadData() {

  }


}
