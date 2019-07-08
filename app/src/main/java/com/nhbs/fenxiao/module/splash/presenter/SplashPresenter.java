package com.nhbs.fenxiao.module.splash.presenter;

import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.module.home.HomePageActivity;
import com.nhbs.fenxiao.module.login.LoginActivity;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.countdown.RxCountDownAdapter;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public class SplashPresenter extends BaseViewPresenter<SplashViewer> {

  private RxCountDown rxCountDown = new RxCountDown();

  public SplashPresenter(SplashViewer viewer) {
    super(viewer);
  }

  public void handleCountDown() {
    rxCountDown.start(1);
    rxCountDown.setCountDownTimeListener(new RxCountDownAdapter() {
      @Override public void onError(Throwable e) {
        super.onError(e);
        getHome();
      }

      @Override public void onNext(Integer time) {
        super.onNext(time);
      }

      @Override public void onComplete() {
        getHome();
        super.onComplete();
      }
    });
  }

  private void getHome() {
    if (UserProfile.getInstance().isLogin()) {
      getLauncherHelper().startActivity(HomePageActivity.class);
    } else {
      getLauncherHelper().startActivity(LoginActivity.class);
    }

    getActivity().finish();
  }

  @Override public void willDestroy() {
    super.willDestroy();
    if (rxCountDown != null) {
      rxCountDown.stop();
    }
  }
}
