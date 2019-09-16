package com.nhbs.fenxiao.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import com.mylhyl.circledialog.CircleDialog;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.TipRequestSubscriber;
import com.nhbs.fenxiao.utils.ActivityManager;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.model.ApiResult;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.utils.RxSchedulerUtils;

@SuppressLint("CheckResult")
public class MineSettingsPresenter extends BaseViewPresenter<MineSettingsViewer> {

  public MineSettingsPresenter(MineSettingsViewer viewer) {
    super(viewer);
  }

   public void logout() {
    CircleDialog.Builder builder = new CircleDialog.Builder();
    builder.setText("温馨提示")
        .setText("\n确定退出登录吗?\n")
        .setPositive("确定", v ->
            XHttp.custom(AppApiServices.class)
        .logout(HttpUtils.getJsonRequestBody(new Object()), UserProfile.getInstance().getAppToken())
         .compose(RxSchedulerUtils.<ApiResult<Object>>_io_main_o())
            .subscribeWith(new TipRequestSubscriber<ApiResult<Object>>() {
              @Override protected void onSuccess(ApiResult<Object> data) {
                ActivityManager.getInstance().reLogin();
              }
            }))
        .setNegative("取消", null)
        .show(getActivity().getSupportFragmentManager());
  }
}