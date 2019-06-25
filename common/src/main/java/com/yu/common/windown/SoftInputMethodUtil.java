package com.yu.common.windown;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftInputMethodUtil {

  /**
   * 隐藏软键盘<br>
   * <strong>注意：</strong>对于自定义AlterDialog或者Dialog以及Activity，可以设置window的属性。<br>
   * <strong>Code：</strong><br>
   * getWindow().setSoftInputMode(
   * WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
   */
  public static void hideSoftInputMethod(Context context, View view) {
    InputMethodManager imm = (InputMethodManager) context.getApplicationContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  /**
   * 显示输入法
   */
  public static void showSoftInputMethod(Context context, View view) {
    InputMethodManager imm = (InputMethodManager) context.getApplicationContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
  }

  /**
   * 切换软键盘状态
   */
  public static void toggleSoftInputMethod(Context context, View view) {
    // 隐藏软键盘
    InputMethodManager imm = (InputMethodManager) context.getApplicationContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInputFromWindow(view.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
    // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
  }




  @Deprecated
  public static void showKeyboard(Activity activity) {
    if (null != activity) {
      InputMethodManager var = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      var.toggleSoftInput(0, 2);
    }
  }

  public static void hideKeyboard(Activity activity) {
    if (null != activity) {
      try {
        View view = activity.getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
          InputMethodManager var = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
          var.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  public static void showKeyboard(Activity activity, boolean isShow) {
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (isShow) {
      if (activity.getCurrentFocus() == null) {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
      } else {
        imm.showSoftInput(activity.getCurrentFocus(), 0);
      }
    } else {
      if (activity.getCurrentFocus() != null) {
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }
}
