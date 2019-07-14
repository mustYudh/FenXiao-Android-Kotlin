package com.yu.common.glide.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author chenwei
 * @date 2017/11/8
 */
public class ImageLoaderConfig {

  public static Context context;

  public static void init(@NonNull Context context) {
    ImageLoaderConfig.context = context.getApplicationContext();
  }
}
