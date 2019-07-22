package com.nhbs.fenxiao.utils;

import android.content.Intent;
import com.nhbs.fenxiao.APP;
import com.nhbs.fenxiao.base.BaseActivity;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.module.login.LoginActivity;
import com.yu.common.launche.LauncherHelper;
import java.util.HashSet;

public class ActivityManager {

  private static HashSet<BaseActivity> hashSet = new HashSet<>();

  private static final ActivityManager ourInstance = new ActivityManager();

  public static ActivityManager getInstance() {
    return ourInstance;
  }

  private ActivityManager() {
  }

  //这就是存在内存中
  public void addActivity(BaseActivity activity) {
    try {
      if (activity != null && !activity.isDestroyed()) {
        hashSet.add(activity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void exit() {
    try {
      for (BaseActivity activity : hashSet) {
        if (activity != null && !activity.isDestroyed()) {
          activity.finish();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void reLogin() {
    UserProfile.getInstance().clean();
    Intent intent = new Intent(APP.getInstance(), LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    LauncherHelper.from(APP.getInstance()).startActivity(intent);
    exit();
  }
}
