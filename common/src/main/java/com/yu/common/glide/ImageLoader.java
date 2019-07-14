package com.yu.common.glide;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.yu.common.glide.callback.BitmapLoadCallback;
import com.yu.common.glide.callback.DrawableLoadedCallback;
import com.yu.common.glide.impl.ImageLoaderCommonFunc;
import com.yu.common.glide.impl.ImageLoaderForGlideImpl;
import com.yu.common.glide.options.Options;

/**
 * @author chenwei
 * @date 2017/11/8
 */
public class ImageLoader implements ImageLoaderCommonFunc {

  private static ImageLoader loader;

  private ImageLoaderCommonFunc impl;

  private ImageLoader() {
    impl = new ImageLoaderForGlideImpl();
  }

  public static synchronized ImageLoader getInstance() {
    if (loader == null) {
      synchronized (ImageLoader.class) {
        if (loader == null) {
          loader = new ImageLoader();
        }
      }
    }
    return loader;
  }

  public void setImageLoaderImpl(@NonNull ImageLoaderCommonFunc impl) {
    this.impl = impl;
  }

  @Override public void displayImage(ImageView imageView, String source) {
    impl.displayImage(imageView, source);
  }

  @Override public void displayImage(ImageView imageView, String source,
      @Nullable DrawableLoadedCallback callback) {
    impl.displayImage(imageView, source, callback);
  }

  @Override public void displayImage(ImageView imageView, String source, int defaultId) {
    impl.displayImage(imageView, source, defaultId);
  }

  @Override public void displayImage(ImageView imageView, String source, int defaultId,
      @Nullable DrawableLoadedCallback callback) {
    impl.displayImage(imageView, source, defaultId, callback);
  }

  @Override
  public void displayImage(ImageView imageView, String source, int placeholderId, int errorId) {
    impl.displayImage(imageView, source, placeholderId, errorId);
  }

  @Override
  public void displayImage(ImageView imageView, String source, int placeholderId, int errorId,
      @Nullable DrawableLoadedCallback callback) {
    impl.displayImage(imageView, source, placeholderId, errorId, callback);
  }

  @Override public void displayImage(ImageView imageView, String source, Options options) {
    impl.displayImage(imageView, source, options);
  }

  @Override public void displayImage(ImageView imageView, String source, Options options,
      @Nullable DrawableLoadedCallback callback) {
    impl.displayImage(imageView, source, options, callback);
  }

  @Override public Bitmap loadBitmap(String url) {
    return impl.loadBitmap(url);
  }

  @Override public Bitmap loadBitmap(String url, int width, int height) {
    return impl.loadBitmap(url, width, height);
  }

  @Override public void loadBitmap(String url, BitmapLoadCallback bitmapLoadCallback) {
    impl.loadBitmap(url, bitmapLoadCallback);
  }

  @Override public String getDiskCachePath(String url) {
    return impl.getDiskCachePath(url);
  }
}
