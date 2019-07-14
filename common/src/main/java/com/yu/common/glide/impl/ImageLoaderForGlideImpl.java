package com.yu.common.glide.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.EmptySignature;
import com.yu.common.glide.GlideApp;
import com.yu.common.glide.GlideRequest;
import com.yu.common.glide.callback.BitmapLoadCallback;
import com.yu.common.glide.callback.DrawableLoadedCallback;
import com.yu.common.glide.config.ImageLoaderConfig;
import com.yu.common.glide.disk.DataCacheKey;
import com.yu.common.glide.options.Options;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * @author chenwei
 * @date 2017/11/8
 */
public class ImageLoaderForGlideImpl implements ImageLoaderCommonFunc {

  @Override public void displayImage(ImageView imageView, String source) {
    displayImage(imageView, source, 0, 0, null);
  }

  @Override public void displayImage(ImageView imageView, String source,
      @Nullable DrawableLoadedCallback callback) {
    displayImage(imageView, source, 0, 0, callback);
  }

  @Override public void displayImage(ImageView imageView, String source, int defaultId) {
    displayImage(imageView, source, defaultId, 0, null);
  }

  @Override public void displayImage(ImageView imageView, String source, int defaultId,
      @Nullable DrawableLoadedCallback callback) {
    displayImage(imageView, source, defaultId, 0, callback);
  }

  @Override public void displayImage(ImageView imageView, String source, Options options) {
    displayImage(imageView, source, options, null);
  }

  @Override public void displayImage(ImageView imageView, String source, Options options,
      @Nullable DrawableLoadedCallback callback) {
    GlideRequest<Drawable> glide = newGlide(imageView, source);
    if (glide != null) {
      if (options.isFadeIn()) {
        glide = glide.transition(DrawableTransitionOptions.withCrossFade());
      }
      if (options.isCircle()) {
        glide = glide.circleCrop();
      }
      onLoadImage(glide, imageView, source, options.getDefaultImageResource(),
          options.getDefaultImageResource(), callback);
    }
  }

  @Override
  public void displayImage(ImageView imageView, String source, int placeholderId, int errorId) {
    displayImage(imageView, source, placeholderId, errorId, null);
  }

  @Override
  public void displayImage(ImageView imageView, final String source, int placeholderId, int errorId,
      @Nullable DrawableLoadedCallback callback) {
    GlideRequest<Drawable> glide = newGlide(imageView, source);
    onLoadImage(glide, imageView, source, placeholderId, errorId, callback);
  }

  /**
   * 实现Glide加载的统一函数
   */
  protected void onLoadImage(GlideRequest<Drawable> glide, ImageView imageView, final String source,
      int placeholderId, int errorId, @Nullable DrawableLoadedCallback callback) {
    if (glide == null) {
      glide = newGlide(imageView, source);
    }
    if (glide == null || imageView == null) {
      return;
    }
    if (placeholderId != 0) {
      glide = glide.placeholder(placeholderId);
    }
    if (errorId != 0) {
      glide = glide.error(errorId);
    }
    if (callback != null) {
      final WeakReference<DrawableLoadedCallback> callbackWeakReference =
          new WeakReference<>(callback);
      glide = glide.listener(new RequestListener<Drawable>() {
        @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
            Target<Drawable> target, boolean isFirstResource) {
          if (callbackWeakReference.get() != null) {
            callbackWeakReference.get().onLoaded(source, null);
          }
          return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
            DataSource dataSource, boolean isFirstResource) {
          if (callbackWeakReference.get() != null) {
            callbackWeakReference.get().onLoaded(source, resource);
          }
          return false;
        }
      });
    }
    glide.into(imageView);
  }

  @Override public Bitmap loadBitmap(String url) {
    if (ImageLoaderConfig.context == null) {
      return null;
    }
    RequestBuilder<Bitmap> builder = GlideApp.with(ImageLoaderConfig.context).asBitmap().load(url);
    try {
      return builder.submit().get();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public Bitmap loadBitmap(String url, int width, int height) {
    if (ImageLoaderConfig.context == null) {
      return null;
    }
    RequestBuilder<Bitmap> builder =
        GlideApp.with(ImageLoaderConfig.context).asBitmap().load(url).override(width, height);
    try {
      return builder.submit().get();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public void loadBitmap(final String url, final BitmapLoadCallback bitmapLoadCallback) {
    AsyncTask.execute(new Runnable() {
      @Override public void run() {
        final Bitmap bitmap = loadBitmap(url);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override public void run() {
            if (bitmapLoadCallback != null) {
              bitmapLoadCallback.onLoadedBitmap(bitmap);
            }
          }
        });
      }
    });
  }

  /**
   * @param imageView
   * @param source
   * @return
   */
  private @Nullable GlideRequest<Drawable> newGlide(ImageView imageView, String source) {
    if (imageView == null) {
      return null;
    }
    Context context = imageView.getContext();
    if (context == null) {
      return null;
    }
    if (context instanceof Activity && ((Activity) context).isFinishing()) {
      return null;
    }
    GlideRequest<Drawable> request = GlideApp.with(ImageLoaderConfig.context).load(source);
    ImageView.ScaleType scaleType = imageView.getScaleType();

    if (scaleType == ImageView.ScaleType.FIT_CENTER) {
      return request.fitCenter();
    } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
      return request.centerCrop();
    } else {
      return request;
    }
  }

  @Override public String getDiskCachePath(String url) {
    DataCacheKey dataCacheKey = new DataCacheKey(new GlideUrl(url), EmptySignature.obtain());
    SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
    String safeKey = safeKeyGenerator.getSafeKey(dataCacheKey);
    try {
      int cacheSize = 100 * 1000 * 1000;
      DiskLruCache diskLruCache = DiskLruCache.open(
          new File(ImageLoaderConfig.context.getCacheDir(),
              DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, cacheSize);
      DiskLruCache.Value value = diskLruCache.get(safeKey);
      File file = value != null ? value.getFile(0) : null;
      if (file != null) {
        return file.getAbsolutePath();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
