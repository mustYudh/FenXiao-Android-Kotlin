package com.nhbs.fenxiao.module.store.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.nhbs.fenxiao.module.store.fragment.MiniStoreGoodsInfoFragment

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreGoodsPageAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return MiniStoreGoodsInfoFragment()
    }

    override fun getCount(): Int {
        return 3
    }

}