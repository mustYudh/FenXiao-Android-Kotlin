package com.nhbs.fenxiao.module.store.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.nhbs.fenxiao.module.store.fragment.AuditRecordsFragment
import com.nhbs.fenxiao.module.store.fragment.MiniStoreActivityFragment
import com.nhbs.fenxiao.module.store.fragment.MiniStoreGoodsInfoFragment

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreGoodsPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> MiniStoreGoodsInfoFragment()
      1 -> MiniStoreActivityFragment()
      else -> AuditRecordsFragment()
    }

  }

  override fun getCount(): Int {
    return 3
  }

}