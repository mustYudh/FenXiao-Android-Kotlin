package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.activity.WithdrawalActivity
import kotlinx.android.synthetic.main.fragment_income_assets_layout.withdrawal

/**
 * @author yudneghao
 * @date 2019-08-18
 */
class IncomeAssetsFragment : BaseFragment() {


  companion object {
    private var fragment: Fragment? = null
    fun getFragment(): Fragment {
      return if (fragment == null) {
        fragment = IncomeAssetsFragment()
        fragment!!
      } else {
        fragment!!
      }

    }
  }

  override fun getContentViewId(): Int {
    return R.layout.fragment_income_assets_layout
  }

  override fun setView(savedInstanceState: Bundle?) {

  }

  override fun loadData() {
    withdrawal.setOnClickListener {
      launchHelper.startActivity(WithdrawalActivity::class.java)
    }
  }

}