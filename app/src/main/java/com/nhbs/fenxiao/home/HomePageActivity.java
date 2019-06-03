package com.nhbs.fenxiao.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.home.presenter.HomePagePresenter;
import com.nhbs.fenxiao.home.presenter.HomePageViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.PressHandle;

public class HomePageActivity extends BaseBarActivity implements HomePageViewer {
  private PressHandle pressHandle = new PressHandle(this);


  @PresenterLifeCycle HomePagePresenter presenter = new HomePagePresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_home_page_view);
  }

  @Override protected void loadData() {

  }

  @Override public void onBackPressed() {
    if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
      super.onBackPressed();
    }
  }
}
