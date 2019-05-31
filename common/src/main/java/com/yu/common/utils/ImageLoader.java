package com.yu.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import java.io.File;
import java.util.concurrent.ExecutionException;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ImageLoader {
    private ImageLoader() {
        throw new RuntimeException("ImageLoader cannot be initialized!");
    }

    /**
     * fitCenter() 是裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView。
     * 如果你想直接显示图片而没有任何淡入淡出效果，在 Glide 的建造者中调用  。
     *
     * @param context
     * @param url
     * @param view
     * @param defaultResId
     */
    public static void loadFit(Context context, String url, ImageView view, int defaultResId) {
        view.setScaleType(ImageView.ScaleType.FIT_XY);  //不按比例缩放图片，目标是把图片塞满整个View
        Glide.with(context).load(url).fitCenter().placeholder(defaultResId).into(view);
    }

    /**
     * CenterCrop()是一个裁剪技术，即缩放图像让它填充到 ImageView 界限内并且侧键额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示。
     * 如果你想直接显示图片而没有任何淡入淡出效果，在 Glide 的建造者中调用  。
     *
     * @param context
     * @param url
     * @param view
     * @param defaultResId
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId) {
        Glide.with(context).load(url).centerCrop().dontAnimate().error(defaultResId).placeholder(defaultResId).into(view);
    }

    public static void loadCenterCrop(Context context, String url, ImageView view) {
        Glide.with(context).load(url).centerCrop().into(view);
    }

    public static void loadFitCenter(Context context, String url, ImageView view, int defaultResId) {
        Glide.with(context).load(url).fitCenter().placeholder(defaultResId).into(view);
    }

    public static void blurTransformation(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .centerCrop()

                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(context, 24, 8))
                .into(view);
    }


    public static void blurTransformation(Context context, String url, ImageView view, int radius, int sampling) {
        Glide.with(context)
                .load(url)
                .centerCrop()

                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(context, radius, sampling))
                .into(view);
    }


    public static void blurTransformation(Context context, int res, ImageView view, int radius, int sampling) {
        Glide.with(context)
                .load(res)
                .centerCrop()

                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(context, radius, sampling))
                .into(view);
    }

    /**
     * 带监听处理
     *
     * @param context
     * @param url
     * @param view
     * @param listener
     */
    public static void loadFitCenter(Context context, String url, ImageView view, RequestListener<? super String, com.bumptech.glide.load.resource.drawable.GlideDrawable> listener) {
        Glide.with(context).load(url).fitCenter().listener(listener).into(view);
    }

    public static void loadCenterCrop(Context context, String url, ImageView view, RequestListener<? super String, com.bumptech.glide.load.resource.drawable.GlideDrawable> listener) {
        Glide.with(context).load(url).centerCrop().listener(listener).into(view);
    }

    /**
     * 设置图片大小处理
     *
     * @param context
     * @param url
     * @param view
     * @param defaultResId
     * @param width
     * @param height
     */
    public static void loadFitOverride(Context context, String url, ImageView view, int defaultResId,
                                       int width, int height) {
        Glide.with(context).load(url).fitCenter().override(width, height)
                .placeholder(defaultResId).into(view);
    }

    public static void loadHader(final Context context, String url, ImageView view) {
        Glide.with(context)
            .load(url)
            .asBitmap()  //这句不能少，否则下面的方法会报错
            .centerCrop()
            .into(new BitmapImageViewTarget(view) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    view.setImageDrawable(circularBitmapDrawable);
                }
            });
    }

    /**
     * 计算图片分辨率
     *
     * @param context
     * @param url
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String calePhotoSize(Context context, String url) throws ExecutionException, InterruptedException {
        File file = Glide.with(context).load(url)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return options.outWidth + "*" + options.outHeight;
    }
}