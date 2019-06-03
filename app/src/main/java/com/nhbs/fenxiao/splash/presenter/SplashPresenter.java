package com.nhbs.fenxiao.splash.presenter;

import com.nhbs.fenxiao.home.HomePageActivity;
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
        super.onComplete();
        getHome();
      }
    });
  }

  private void getHome() {
    getLauncherHelper().startActivity(HomePageActivity.class);
    getActivity().finish();
  }

  @Override public void willDestroy() {
    super.willDestroy();
    if (rxCountDown != null) {
      rxCountDown.stop();
    }
  }
}
