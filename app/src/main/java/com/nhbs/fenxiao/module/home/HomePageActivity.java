package com.nhbs.fenxiao.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseActivity;
import com.nhbs.fenxiao.module.center.HomeCenterPopUpWindow;
import com.nhbs.fenxiao.module.home.fragment.HomeFragment;
import com.nhbs.fenxiao.module.home.presenter.HomePagePresenter;
import com.nhbs.fenxiao.module.home.presenter.HomePageViewer;
import com.nhbs.fenxiao.module.mine.fragment.MineFragment;
import com.nhbs.fenxiao.module.product.fragment.ProductFragment;
import com.nhbs.fenxiao.module.store.fragment.MiniStoreFragment;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.PressHandle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity implements HomePageViewer {

    private PressHandle pressHandle = new PressHandle(this);

    @PresenterLifeCycle
    private HomePagePresenter mPresenter = new HomePagePresenter(this);
    private BottomNavigationView mBottomNavigationView;
    private HomeCenterPopUpWindow mHomePopUpWindow;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_page_view);
    }

    @Override
    protected void loadData() {
        List<TabItem> items = new ArrayList<>();
        mBottomNavigationView = bindView(R.id.navigation_view);
        items.add(new TabView(0, new HomeFragment()));
        items.add(new TabView(1, new ProductFragment()));
        items.add(new TabView(2, null));
        items.add(new TabView(3, new MiniStoreFragment()));
        items.add(new TabView(4, new MineFragment()));
        mBottomNavigationView.initControl(this).setPagerView(items, 0);
        mBottomNavigationView.getControl().setOnTabClickListener((position, view) -> {
            if (position == 2) {
                mHomePopUpWindow = new HomeCenterPopUpWindow(HomePageActivity.this);
                mHomePopUpWindow.showPopupWindow();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mHomePopUpWindow != null && mHomePopUpWindow.isShowing()) {
            mHomePopUpWindow.dismiss();
        } else {
            if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
                super.onBackPressed();
            }
        }
    }
}
