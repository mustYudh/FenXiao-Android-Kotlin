package com.denghao.control;

import android.support.v4.app.Fragment;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class TabView implements TabItem {
  private Fragment mFragment;
  private int position;

  /**
   * @param fragment 当前导航栏对应的Fragment
   */
  public TabView(int position, Fragment fragment) {
    this.position = position;
    this.mFragment = fragment;
  }

  @Override public Fragment getCurrentFragment() {
    return mFragment;
  }

  @Override public int getPosition() {
    return position;
  }

  @Override public String getTag() {
    if (mFragment != null) {
      return mFragment.getClass().getSimpleName();
    } else {
      return "";
    }
  }

  @Override public void setMessageHint(int count) {

  }
}
