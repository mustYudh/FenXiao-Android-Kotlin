package com.yu.common.glide.impl;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.yu.common.glide.callback.BitmapLoadCallback;
import com.yu.common.glide.callback.DrawableLoadedCallback;
import com.yu.common.glide.options.Options;

/**
 * @author chenwei
 * @date 2017/11/8
 */
public interface ImageLoaderCommonFunc {

  void displayImage(ImageView imageView, String source);

  void displayImage(ImageView imageView, String source, @Nullable DrawableLoadedCallback callback);

  void displayImage(ImageView imageView, String source, @DrawableRes int defaultId);

  void displayImage(ImageView imageView, String source, @DrawableRes int defaultId,
      @Nullable DrawableLoadedCallback callback);

  void displayImage(ImageView imageView, String source, @DrawableRes int placeholderId,
      @DrawableRes int errorId);

  void displayImage(ImageView imageView, String source, @DrawableRes int placeholderId,
      @DrawableRes int errorId, @Nullable DrawableLoadedCallback callback);

  void displayImage(ImageView imageView, String source, Options options);

  void displayImage(ImageView imageView, String source, Options options,
      @Nullable DrawableLoadedCallback callback);

  Bitmap loadBitmap(String url);

  Bitmap loadBitmap(String url, int width, int height);

  void loadBitmap(String url, BitmapLoadCallback bitmapLoadCallback);

  String getDiskCachePath(String url);
}
