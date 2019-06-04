package com.yu.common.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author chenwei
 * @date 2017/11/20
 */
public class LoginRedirectHelper {

  private final static String LoginExtraBundle = "LoginExtraBundle";
  private final static String RedirectActivityClassName = "RedirectActivityClassName";
  private final static String RedirectOtherAction = "RedirectOtherAction";

  public static Intent setRedirectData(Context context, Class<? extends Activity> loginClass,
      Bundle loginExtraBundle, Class<? extends Activity> redirectActivityClass,
      String otherAction) {
    Intent intent = new Intent(context, loginClass);
    intent.putExtra(LoginExtraBundle, BundleHelper.create(loginExtraBundle)
        .putString(RedirectActivityClassName,
            redirectActivityClass == null ? "" : redirectActivityClass.getName())
        .putString(RedirectOtherAction, otherAction)
        .get());
    return intent;
  }

  public static Intent setRedirectData(Context context, Class<? extends Activity> loginClass,
      Bundle loginExtraBundle, String redirectActivityClassName, String otherAction) {
    Intent intent = new Intent(context, loginClass);
    intent.putExtra(LoginExtraBundle, BundleHelper.create(loginExtraBundle)
        .putString(RedirectActivityClassName, redirectActivityClassName)
        .putString(RedirectOtherAction, otherAction)
        .get());
    return intent;
  }

  /**
   * 用于抽取Activity的Intent中重定向登录数据
   */
  public static @NonNull Bundle getRedirectExtraBundle(Activity activity) {
    Bundle bundle = new Bundle();
    if (activity == null || activity.getIntent() == null) {
      return bundle;
    }
    bundle.putBundle(LoginExtraBundle,
        BundleHelper.create(activity.getIntent().getBundleExtra(LoginExtraBundle)).get());
    return bundle;
  }

  public static Bundle getLoginExtraBundle(Activity activity) {
    if (activity == null || activity.getIntent() == null) {
      return null;
    }
    return activity.getIntent().getBundleExtra(LoginExtraBundle);
  }

  public static String getRedirectActivityClassName(Activity activity) {
    Bundle extraBundle = getLoginExtraBundle(activity);
    if (extraBundle == null) {
      return null;
    }
    return extraBundle.getString(RedirectActivityClassName);
  }

  public static String getRedirectOtherAction(Activity activity) {
    Bundle extraBundle = getLoginExtraBundle(activity);
    if (extraBundle == null) {
      return null;
    }
    return extraBundle.getString(RedirectOtherAction);
  }
}
