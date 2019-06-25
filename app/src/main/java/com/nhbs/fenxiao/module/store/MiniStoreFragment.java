package com.nhbs.fenxiao.module.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.yu.common.navigation.StatusBarFontColorUtil;

/**
 * @author yudenghao
 * @date 2019-06-04
 */
public class MiniStoreFragment extends BaseFragment {
  @Override protected int getContentViewId() {
    return R.layout.fragment_mini_store_layout;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {

  }

  @Override protected void loadData() {

  }


  @Override protected void onPageInTop() {
    super.onPageInTop();
    StatusBarColorManager.INSTANCE.setDark(true);
    StatusBarFontColorUtil.StatusBarLightMode(getActivity());
  }
}
