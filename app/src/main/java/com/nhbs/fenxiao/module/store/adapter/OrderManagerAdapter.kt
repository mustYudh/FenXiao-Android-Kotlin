package com.nhbs.fenxiao.module.store.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.nhbs.fenxiao.module.store.fragment.OrderManagerFragment

/**
 * @author yudneghao
 * @date 2019-08-18
 */
class OrderManagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


  override fun getItem(p0: Int): Fragment {
    return OrderManagerFragment()
  }

  override fun getCount(): Int {
    return 2
  }

}