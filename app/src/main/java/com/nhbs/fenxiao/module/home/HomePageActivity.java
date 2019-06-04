package com.nhbs.fenxiao.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import com.denghao.control.TabItem;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.presenter.HomePagePresenter;
import com.nhbs.fenxiao.module.home.presenter.HomePageViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.PressHandle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseBarActivity implements HomePageViewer {
  private PressHandle pressHandle = new PressHandle(this);

  @PresenterLifeCycle HomePagePresenter presenter = new HomePagePresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_home_page_view);
  }

  @Override protected void loadData() {
    List<TabItem> items = new ArrayList<>();
    //items.add(new TabView(createTabView("首页", R.drawable.tab_01), ));
    //items.add(new TabView(createTabView("商品", R.drawable.tab_03), ));
    //items.add(new TabView(createTabView("小店", R.drawable.tab_02), ));
    //items.add(new TabView(createTabView("我的", R.drawable.tab_04), ));
  }

  //public View createTabView(String name, int drawable) {
  //  View view =
  //      LayoutInflater.from(this).inflate(R.layout.home_table_layout, mNavigationView, false);
  //  ImageView imageView = view.findViewById(R.id.tab_icon);
  //  TextView tabName = view.findViewById(R.id.tab_name);
  //  imageView.setImageResource(drawable);
  //  tabName.setText(name);
  //  return view;
  //}

  @Override public void onBackPressed() {
    if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
      super.onBackPressed();
    }
  }
}
