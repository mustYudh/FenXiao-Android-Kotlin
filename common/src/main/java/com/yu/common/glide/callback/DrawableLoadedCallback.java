package com.yu.common.glide.callback;

import android.graphics.drawable.Drawable;

public interface DrawableLoadedCallback {

  void onLoaded(String url, Drawable drawable);
}
