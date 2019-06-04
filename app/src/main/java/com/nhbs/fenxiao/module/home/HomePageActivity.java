package com.nhbs.fenxiao.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseActivity;
import com.nhbs.fenxiao.module.home.fragment.HomeFragment;
import com.nhbs.fenxiao.module.home.presenter.HomePagePresenter;
import com.nhbs.fenxiao.module.home.presenter.HomePageViewer;
import com.nhbs.fenxiao.module.mine.fragment.MineFragment;
import com.nhbs.fenxiao.module.product.fragment.ProductFragment;
import com.nhbs.fenxiao.module.store.MiniStoreFragment;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.PressHandle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity implements HomePageViewer {

  private PressHandle pressHandle = new PressHandle(this);

  @PresenterLifeCycle HomePagePresenter presenter = new HomePagePresenter(this);
  private BottomNavigationView mBottomNavigationView;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_home_page_view);
  }

  @Override protected void loadData() {
    List<TabItem> items = new ArrayList<>();
    mBottomNavigationView = bindView(R.id.navigation_view);
    items.add(new TabView(createTabView("首页", R.drawable.tab_home_selector), new HomeFragment()));
    items.add(
        new TabView(createTabView("商品", R.drawable.tab_product_selector), new ProductFragment()));
    items.add(new TabView(createTabView("小店", R.drawable.tab_mini_store_selector),
        new MiniStoreFragment()));
    items.add(new TabView(createTabView("我的", R.drawable.tab_nime_selector), new MineFragment()));
    mBottomNavigationView.initControl(this).setPagerView(items, 0);
    mBottomNavigationView.getNavgation().setTabControlHeight(60);
  }

  public View createTabView(String name, int drawable) {
    View view =
        LayoutInflater.from(this).inflate(R.layout.home_table_layout, mBottomNavigationView, false);
    ImageView imageView = view.findViewById(R.id.tab_icon);
    TextView tabName = view.findViewById(R.id.tab_name);
    imageView.setImageResource(drawable);
    tabName.setText(name);
    return view;
  }

  @Override public void onBackPressed() {
    if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
      super.onBackPressed();
    }
  }
}
