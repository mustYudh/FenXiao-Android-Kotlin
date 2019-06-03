package ${packageName};

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarFragment;
import ${fragmentName}.presenter.${fragmentClass}Viewer;
import ${fragmentName}.presenter.${fragmentClass}Presenter;
import com.yu.common.mvp.PresenterLifeCycle;


public class ${fragmentClass}Fragment extends BaseBarFragment implements ${fragmentClass}Viewer {

 @PresenterLifeCycle ${fragmentClass}Presenter presenter = new ${fragmentClass}Presenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.${layoutName}_view);
  }

  @Override protected void loadData() {

  }
}
