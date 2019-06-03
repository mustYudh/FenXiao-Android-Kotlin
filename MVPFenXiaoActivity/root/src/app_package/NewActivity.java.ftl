package ${packageName};

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import ${packageName}.presenter.${activityClass}Presenter;
import ${packageName}.presenter.${activityClass}Viewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class ${activityClass}Activity extends BaseBarActivity implements ${activityClass}Viewer {

 @PresenterLifeCycle ${activityClass}Presenter presenter = new ${activityClass}Presenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.${layoutName}_view);
  }

  @Override protected void loadData() {

  }
}
