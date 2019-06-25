package com.yu.common.glide.config;

import android.content.Context;
import android.support.annotation.NonNull;

public class ImageLoaderConfig {

  public static Context context;

  public static void init(@NonNull Context context) {
    ImageLoaderConfig.context = context.getApplicationContext();
  }
}
