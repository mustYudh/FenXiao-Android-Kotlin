package com.yu.common.glide.callback;

import android.graphics.drawable.Drawable;

/**
 * @author chenwei
 * @date 2018/7/25
 */
public interface DrawableLoadedCallback {

  void onLoaded(String url, Drawable drawable);
}
