package com.nhbs.fenxiao.module.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.yu.common.navigation.StatusBarFontColorUtil;
import com.yu.common.ui.BadgeView;

/**
 * @author yudenghao
 * @date 2019-06-04
 */
public class MiniStoreFragment extends BaseBarFragment {

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_page_fragment_mini_store_layout;
    }

    @Override
    public boolean isImmersionBar() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mini_store_layout;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        BadgeView badgeView = bindView(R.id.badge_view);
        badgeView.setBadgeCount(1);
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void onPageInTop() {
        super.onPageInTop();
        StatusBarColorManager.INSTANCE.setDark(false);
        StatusBarFontColorUtil.statusBarDarkMode(getActivity());
    }
}
