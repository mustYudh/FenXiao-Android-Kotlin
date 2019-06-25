package com.yu.common.navigation;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarFontColorUtil {


  /**
   * 设置为黑字
   */
  public static int setStatusBarBlackFontMode(Activity activity) {
    return StatusBarLightMode(activity);
  }

  /**
   * 设置为白字
   */
  public static int setStatusBarWhiteFontMode(Activity activity) {
    return statusBarDarkMode(activity);
  }

  @Deprecated public static int StatusBarLightMode(Activity activity) {
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (setMIUIStatusBarLightMode(activity, true)) {
        result = 1;
      } else if (setFlymeStatusBarLightMode(activity.getWindow(), true)) {
        result = 2;
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activity.getWindow()
            .getDecorView()
            .setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        result = 3;
      } else {
        StatusBarUtils.setTranslucent(activity, 80);
      }
    }
    return result;
  }

  @Deprecated public static int statusBarDarkMode(Activity activity) {
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (setMIUIStatusBarLightMode(activity, false)) {
        result = 1;
      } else if (setFlymeStatusBarLightMode(activity.getWindow(), false)) {
        result = 2;
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activity.getWindow()
            .getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        result = 3;
      } else {
        StatusBarUtils.setTranslucent(activity, 0);
      }
    }
    return result;
  }

  /**
   * 设置状态栏图标为深色和魅族特定的文字风格
   * 可以用来判断是否为Flyme用户
   *
   * @param window 需要设置的窗口
   * @param dark 是否把状态栏文字及图标颜色设置为深色
   * @return boolean 成功执行返回true
   */
  private static boolean setFlymeStatusBarLightMode(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      try {
        WindowManager.LayoutParams lp = window.getAttributes();
        Field darkFlag =
            WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
        Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
        darkFlag.setAccessible(true);
        meizuFlags.setAccessible(true);
        int bit = darkFlag.getInt(null);
        int value = meizuFlags.getInt(lp);
        if (dark) {
          value |= bit;
        } else {
          value &= ~bit;
        }
        meizuFlags.setInt(lp, value);
        window.setAttributes(lp);
        result = true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * 需要MIUIV6以上
   *
   * @param dark 是否把状态栏文字及图标颜色设置为深色
   * @return boolean 成功执行返回true
   */
  private static boolean setMIUIStatusBarLightMode(Activity activity, boolean dark) {
    boolean result = false;
    Window window = activity.getWindow();
    if (window != null) {
      Class clazz = window.getClass();
      try {
        int darkModeFlag = 0;
        Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        if (dark) {
          //状态栏透明且黑色字体
          extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
        } else {
          //清除黑色字体
          extraFlagField.invoke(window, 0, darkModeFlag);
        }
        result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
          if (dark) {
            activity.getWindow()
                .getDecorView()
                .setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
          } else {
            activity.getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

}
