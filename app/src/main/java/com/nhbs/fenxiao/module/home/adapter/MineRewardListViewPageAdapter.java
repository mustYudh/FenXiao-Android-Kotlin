package com.nhbs.fenxiao.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nhbs.fenxiao.base.BaseFragment;

import java.util.List;

/**
 * <pre>
 *     author : mks
 *     e-mail : xxx@xx
 *     time   : 2018/06/23
 *     desc   :
 * </pre>
 */

public class MineRewardListViewPageAdapter extends FragmentPagerAdapter {
    private List<String> list;
    private List<BaseFragment> fragments;

    public MineRewardListViewPageAdapter(FragmentManager fm, List<String> list, List<BaseFragment> fragments) {
        super(fm);
        this.list = list;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
